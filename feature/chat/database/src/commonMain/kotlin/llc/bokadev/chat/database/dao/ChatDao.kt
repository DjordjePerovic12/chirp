package llc.bokadev.chat.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import llc.bokadev.chat.database.entities.ChatEntity
import llc.bokadev.chat.database.entities.ChatInfoEntity
import llc.bokadev.chat.database.entities.ChatMessageEntity
import llc.bokadev.chat.database.entities.ChatParticipantCrossRef
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
    @Transaction
    fun getChatsWithParticipants(): Flow<List<ChatWithParticipants>>


    @Query(
        """
        SELECT DISTINCT c.*
        FROM chatentity c
        JOIN chatparticipantcrossref cpcr ON c.chatId = cpcr.chatId
        WHERE cpcr.isActive = 1
        ORDER BY lastActivityAt DESC
    """
    )
    @Transaction
    fun getChatsWithActiveParticipants(): Flow<List<ChatWithParticipants>>


    @Query("SELECT * FROM chatentity WHERE chatId = :id")
    @Transaction
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
    fun getChatCount(): Flow<Int>

    @Query(
        """
        SELECT p.*
        FROM chatparticipantentity p
        JOIN chatparticipantcrossref cpcr ON p.userId = cpcr.userId
        WHERE cpcr.chatId = :chatId AND cpcr.isActive = true
        ORDER BY p.username
    """
    )
    fun getActiveParticipantsByChatId(chatId: String): Flow<List<ChatParticipantEntity>>


    @Query("""
        SELECT c.*
        FROM chatentity c
        JOIN chatparticipantcrossref cpcr ON c.chatId = cpcr.chatId
        WHERE c.chatId = :chatId AND cpcr.isActive = true
    """)
    @Transaction
    fun getChatInfoById(chatId: String): Flow<ChatInfoEntity?>


    suspend fun upsertChatWithParticipantsAndCrossRefs(
        chat: ChatEntity,
        participants: List<ChatParticipantEntity>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao
    ) {
        upsertChat(chat)
        participantDao.upsertParticipants(participants)
        val crossRegs = participants.map {
            ChatParticipantCrossRef(
                chatId = chat.chatId,
                userId = it.userId,
                isActive = true
            )
        }
        crossRefDao.upsertCrossRefs(crossRegs)
        crossRefDao.syncParticipants(chat.chatId, participants)
    }

    suspend fun upsertChatsWithParticipantsAndCrossRefs(
        chats: List<ChatWithParticipants>,
        participantDao: ChatParticipantDao,
        crossRefDao: ChatParticipantsCrossRefDao,
        messageDao: ChatMessageDao
    ) {
        upsertChats(chats.map { it.chat })

        val serverChatIds = chats.map { it.chat.chatId }
        val localChatIds = getAllChatIds()
        val staleChatIds = localChatIds - serverChatIds


        chats.forEach { chat ->
            chat.lastMessage?.run {
                messageDao.upsertMessage(
                    ChatMessageEntity(
                        messageId = messageId,
                        chatId = chatId,
                        senderId = senderId,
                        content = content,
                        timestamp = timestamp,
                        deliveryStatus = deliveryStatus
                    )
                )
            }
        }

        val allParticipants = chats.flatMap { it.participants }
        participantDao.upsertParticipants(allParticipants)

        val allCrossRefs = chats.flatMap { chatWithParticipants ->
            chatWithParticipants.participants.map { participant ->
                ChatParticipantCrossRef(
                    chatId = chatWithParticipants.chat.chatId,
                    userId = participant.userId,
                    isActive = true
                )
            }
        }
        crossRefDao.upsertCrossRefs(allCrossRefs)

        chats.forEach { chat ->
            crossRefDao.syncParticipants(
                chatId = chat.chat.chatId,
                participants = chat.participants
            )
        }



        deleteChatsByIds(staleChatIds)
    }
}