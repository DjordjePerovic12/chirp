package llc.bokadev.chat.domain.message

import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result

interface ChatMessageService {

    suspend fun fetchMessages(
        chatId: String,
        before: String? = null
    ): Result<List<ChatMessage>, DataError.Remote>
}