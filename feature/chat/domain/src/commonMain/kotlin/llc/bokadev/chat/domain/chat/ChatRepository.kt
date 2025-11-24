package llc.bokadev.chat.domain.chat

import kotlinx.coroutines.flow.Flow
import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>
}