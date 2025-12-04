package llc.bokadev.chat.data.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.serialization.json.Json
import llc.bokadev.chat.data.chat.KtorChatParticipantService
import llc.bokadev.chat.data.chat.KtorChatService
import llc.bokadev.chat.data.chat.OfflineFirstChatRepository
import llc.bokadev.chat.data.chat.WebSocketChatConnectionClient
import llc.bokadev.chat.data.message.KtorChatMessageService
import llc.bokadev.chat.data.message.OfflineFirstMessageRepository
import llc.bokadev.chat.data.network.ConnectionRetryHandler
import llc.bokadev.chat.data.network.KtorWebSocketConnector
import llc.bokadev.chat.database.DatabaseFactory
import llc.bokadev.chat.domain.chat.ChatConnectionClient
import llc.bokadev.chat.domain.chat.ChatParticipantService
import llc.bokadev.chat.domain.chat.ChatRepository
import llc.bokadev.chat.domain.chat.ChatService
import llc.bokadev.chat.domain.message.ChatMessageService
import llc.bokadev.chat.domain.message.MessageRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


expect val platformChatDataModule: Module
val chatDataModule = module {
    includes(platformChatDataModule)

    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
    singleOf(::OfflineFirstChatRepository) bind ChatRepository::class
    singleOf(::OfflineFirstMessageRepository) bind MessageRepository::class
    singleOf(::WebSocketChatConnectionClient) bind ChatConnectionClient::class
    singleOf(::ConnectionRetryHandler)
    singleOf(::KtorWebSocketConnector)
    singleOf(::KtorChatMessageService) bind ChatMessageService::class
    single {
        Json {
            ignoreUnknownKeys = true
        }
    }
    single {
        get<DatabaseFactory>()
            .create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
}