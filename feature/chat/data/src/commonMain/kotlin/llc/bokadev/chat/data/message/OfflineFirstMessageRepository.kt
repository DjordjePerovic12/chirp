package llc.bokadev.chat.data.message

import llc.bokadev.chat.database.ChirpChatDatabase
import llc.bokadev.chat.domain.message.MessageRepository
import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.core.data.database.safeDatabaseUpdate
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.asEmptyResult
import kotlin.time.Clock

class OfflineFirstMessageRepository(
    private val database: ChirpChatDatabase
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
}