package llc.bokadev.chat.data.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import io.ktor.websocket.send
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import llc.bokadev.chat.data.dto.websocket.WebSocketMessageDto
import llc.bokadev.chat.data.lifecycle.AppLifecycleObserver
import llc.bokadev.chat.domain.models.ConnectionState
import llc.bokadev.core.data.networking.UrlConstants.BASE_URL_WS
import llc.bokadev.core.domain.auth.SessionStorage
import llc.bokadev.core.domain.logging.ChirpLogger
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result
import llc.bokadev.feature.chat.data.BuildKonfig
import kotlin.coroutines.coroutineContext
import kotlin.time.Duration.Companion.seconds

class KtorWebSocketConnector(
    private val httpClient: HttpClient,
    private val applicationScope: CoroutineScope,
    private val sessionStorage: SessionStorage,
    private val json: Json,
    private val connectionErrorHandler: ConnectionErrorHandler,
    private val connectionRetryHandler: ConnectionRetryHandler,
    private val appLifecycleObserver: AppLifecycleObserver,
    private val connectivityObserver: ConnectivityObserver,
    private val logger: ChirpLogger
) {

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState = _connectionState.asStateFlow()

    private var currentSession: WebSocketSession? = null

    @OptIn(FlowPreview::class)
    private val isConnected = connectivityObserver
        .isConnected
        .debounce(1.seconds)
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    private val isInForeground = appLifecycleObserver
        .isInForeground
        .onEach { isInForeground ->
            if (isInForeground) {
                connectionRetryHandler.resetDelay()
            }
        }
        .stateIn(
            applicationScope,
            SharingStarted.WhileSubscribed(5000L),
            false
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val messages = combine(
        sessionStorage.observeAuthInfo(),
        isConnected,
        isInForeground
    ) { authInfo, isConnected, isInForeground ->
        when {
            authInfo == null -> {
                logger.info("No authentication details. Clearing session and disconnecting...")
                _connectionState.value = ConnectionState.DISCONNECTED
                currentSession?.close()
                currentSession = null
                connectionRetryHandler.resetDelay()
                null
            }

            !isInForeground -> {
                logger.info("App in background, disconnecting socket proactively.")
                _connectionState.value = ConnectionState.DISCONNECTED
                currentSession?.close()
                currentSession = null
                null
            }

            !isConnected -> {
                logger.info("Device disconnected, closing WebSocket connection.")
                _connectionState.value = ConnectionState.ERROR_NETWORK
                currentSession?.close()
                currentSession = null
                null
            }

            else -> {
                logger.info("App in foreground and connected, establishing connection...")
                if (_connectionState.value !in listOf(
                        ConnectionState.CONNECTING,
                        ConnectionState.CONNECTED
                    )
                ) {
                    _connectionState.value = ConnectionState.CONNECTING
                }
                authInfo
            }
        }
    }.flatMapMerge { authInfo ->
        if (authInfo == null) {
            emptyFlow()
        } else {
            createWebSocketFlow(authInfo.accessToken)
                .catch { e ->
                    logger.error("Exception in WS", e)

                    currentSession?.close()
                    currentSession = null

                    val transformedException = connectionErrorHandler.transformException(e)
                    throw transformedException
                }
                .retryWhen { t, attempt ->
                    logger.info("Connection failed on attempt $attempt")

                    val shouldRetry = connectionRetryHandler.shouldRetry(t, attempt)

                    if (shouldRetry) {
                        _connectionState.value = ConnectionState.CONNECTING
                        connectionRetryHandler.applyRetryDelay(attempt)
                    }
                    shouldRetry
                }
                .catch { e ->
                    logger.error("Unhandled WS error", e)

                    _connectionState.value = connectionErrorHandler.getConnectionStateForError(e)
                }
        }
    }

    private fun createWebSocketFlow(accessToken: String) = callbackFlow {
        _connectionState.value = ConnectionState.CONNECTING

        currentSession = httpClient.webSocketSession(
            urlString = "${BASE_URL_WS}/chat"
        ) {
            header("Authorization", "Bearer $accessToken")
            header("X-API-key", BuildKonfig.API_KEY)
        }

        currentSession?.let { session ->
            _connectionState.value = ConnectionState.CONNECTED

            session
                .incoming
                .consumeAsFlow()
                .buffer(
                    capacity = 100
                )
                .collect { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            val text = frame.readText()
                            logger.info("Received raw text frame: $text")

                            val messageDto = json.decodeFromString<WebSocketMessageDto>(text)
                            send(messageDto)
                        }

                        is Frame.Ping -> {
                            logger.debug("Received ping from server. Sending pong.")
                            session.send(Frame.Pong(frame.data))
                        }

                        else -> Unit
                    }
                }
        } ?: throw Exception("Failed to establish WebSocket connection")

        awaitClose {
            launch(NonCancellable) {
                logger.info("Disconnecting from WebSocket session...")
                _connectionState.value = ConnectionState.DISCONNECTED
                currentSession?.close()
                currentSession = null
            }
        }
    }

    suspend fun sendMessage(message: String): EmptyResult<DataError.Connection> {
        val connectionState = connectionState.value

        if (currentSession == null || connectionState != ConnectionState.CONNECTED) {
            return Result.Failure(DataError.Connection.NOT_CONNECTED)
        }

        return try {
            currentSession?.send(message)
            Result.Success(Unit)
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            logger.error("Unable to send WS message", e)
            Result.Failure(DataError.Connection.MESSAGE_SEND_FAILED)
        }
    }

}