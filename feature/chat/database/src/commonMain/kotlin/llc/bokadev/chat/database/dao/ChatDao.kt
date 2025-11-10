package llc.bokadev.chat.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import llc.bokadev.chat.database.entities.ChatEntity
import llc.bokadev.chat.database.entities.ChatInfoEntity
import llc.bokadev.chat.database.entities.ChatParticipantEntity
import llc.bokadev.chat.database.entities.ChatWithParticipants

@Dao
interface ChatDao {

    @Upsert
    suspend fun upsertChat(chatEntity: ChatEntity)

    @Upsert
    suspend fun upsertChats(chats: List<ChatEntity>)

    @Query(
        "DELETE FROM chatentity WHERE chatId = :chatId"
    )
    suspend fun deleteChatById(chatId: String)

    @Query("SELECT * FROM chatentity ORDER BY lastActivityAt DESC")
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>


    @Query("SELECT * FROM chatentity WHERE chatId = :id")
    suspend fun getChatById(id: String): ChatWithParticipants

    @Query("DELETE FROM chatentity")
    suspend fun deleteAllChats()

    @Query("SELECT chatId FROM  chatentity")
    suspend fun getAllChatIds(): List<String>


    @Transaction
    suspend fun deleteChatsByIds(chatIds: List<String>) {
        chatIds.forEach { chatId ->
            deleteChatById(chatId)
        }
    }

    @Query("SELECT COUNT(*) FROM chatentity")
    suspend fun getChatCount(): Flow<Int>

    @Query("""
        SELECT p.*
        FROM chatparticipantentity p
        JOIN chatparticipantcrossref cpcr ON p.userId = cpcr.userId
        WHERE cpcr.chatId = :chatId AND cpcr.isActive = true
        ORDER BY p.username
    """)
    fun getActiveParticipantsByChatId(chatId: String): Flow<List<ChatParticipantEntity>>


    @Query("SELECT * FROM chatentity WHERE chatId = :chatId")
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity>

}