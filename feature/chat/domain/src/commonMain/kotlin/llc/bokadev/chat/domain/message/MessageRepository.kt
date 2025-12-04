package llc.bokadev.chat.domain.message

import kotlinx.coroutines.flow.Flow
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.chat.domain.models.MessageWithSender
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result

interface MessageRepository {
    suspend fun updateMessageDeliveryStatus(
        messageId: String,
        status: ChatMessageDeliveryStatus
    ): EmptyResult<DataError.Local>

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError>

    fun getMessagesForChat(chatId: String): Flow<List<MessageWithSender>>


}