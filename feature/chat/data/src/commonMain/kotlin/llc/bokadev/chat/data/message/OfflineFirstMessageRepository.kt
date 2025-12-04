package llc.bokadev.chat.data.message

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import llc.bokadev.chat.data.mappers.toDomain
import llc.bokadev.chat.data.mappers.toEntity
import llc.bokadev.chat.database.ChirpChatDatabase
import llc.bokadev.chat.domain.message.ChatMessageService
import llc.bokadev.chat.domain.message.MessageRepository
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.chat.domain.models.MessageWithSender
import llc.bokadev.core.data.database.safeDatabaseUpdate
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.asEmptyResult
import llc.bokadev.core.domain.util.onSuccess
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase,
    private val messageService: ChatMessageService
) : MessageRepository {
    override suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local> {
        return safeDatabaseUpdate {
            database.chatMessageDao.updateDeliveryStatus(
                messageId = messageId,
                status = status.name,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }.asEmptyResult()
    }

    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError> {
        return messageService
            .fetchMessages(chatId, before)
            .onSuccess { messages ->
                return safeDatabaseUpdate {
                    database.chatMessageDao.upsertMessagesAndSyncIfNecessary(
                        chatId = chatId,
                        serverMessages = messages.map { it.toEntity() },
                        pageSize = ChatMessageConstants.PAGE_SIZE,
                        shouldSync = before == null //only sync for most recent page
                    )
                    messages
                }
            }
    }

    override fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>> {
        return database
            .chatMessageDao
            .getMessagesByChatId(chatId)
            .map { messages ->
                messages.map { it.toDomain() }
            }
    }
}