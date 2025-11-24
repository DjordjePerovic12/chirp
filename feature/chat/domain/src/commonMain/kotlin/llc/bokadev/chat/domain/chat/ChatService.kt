package llc.bokadev.chat.domain.chat

import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result

interface ChatService {
    suspend fun createChat(
        otherUserIds: List<String>
    ): Result<Chat, DataError.Remote>

    suspend fun getChats(): Result<List<Chat>, DataError.Remote>

}