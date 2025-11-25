package llc.bokadev.chat.domain.chat

import kotlinx.coroutines.flow.Flow
import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.chat.domain.models.ChatInfo
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result

interface ChatRepository {
    fun getChats(): Flow<List<Chat>>
    fun getChatInfoById(chatId: String): Flow<ChatInfo>
    suspend fun fetchChats(): Result<List<Chat>, DataError.Remote>

    suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote>

    suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote>
    suspend fun leaveChat(chatId: String): EmptyResult<DataError.Remote>
}