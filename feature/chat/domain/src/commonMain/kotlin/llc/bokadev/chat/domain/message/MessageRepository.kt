package llc.bokadev.chat.domain.message

import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>
}