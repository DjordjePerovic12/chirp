package llc.bokadev.chat.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.json.Json
import llc.bokadev.chat.data.dto.websocket.WebSocketMessageDto
import llc.bokadev.chat.data.mappers.toNewMessage
import llc.bokadev.chat.data.network.KtorWebSocketConnector
import llc.bokadev.chat.database.ChirpChatDatabase
import llc.bokadev.chat.domain.chat.ChatConnectionClient
import llc.bokadev.chat.domain.chat.ChatRepository
import llc.bokadev.chat.domain.error.ConnectionError
import llc.bokadev.chat.domain.message.MessageRepository
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.chat.domain.models.ConnectionState
import llc.bokadev.core.domain.auth.SessionStorage
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.onFailure

class WebSocketChatConnectionClient(
    private val webSocketConnector: KtorWebSocketConnector,
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val database: ChirpChatDatabase,
    private val sessionStorage: SessionStorage,
    private val json: Json
) : ChatConnectionClient {
    override val chatMessages: Flow<ChatMessage>
        get() = TODO("Not yet implemented")


    override val connectionState = webSocketConnector.connectionState

    override suspend fun sendChatMessage(message: ChatMessage): EmptyResult<ConnectionError> {
        val outgoingDto = message.toNewMessage()
        val webSocketMessage = WebSocketMessageDto(
            type = outgoingDto.type.name,
            payload = json.encodeToString(outgoingDto)
        )

        val rawJsonPayload = json.encodeToString(webSocketMessage)

        return webSocketConnector
            .sendMessage(rawJsonPayload)
            .onFailure { error ->
                messageRepository.updateMessageDeliveryStatus(
                    messageId = message.id,
                    status = ChatMessageDeliveryStatus.FAILED
                )
            }
    }
}