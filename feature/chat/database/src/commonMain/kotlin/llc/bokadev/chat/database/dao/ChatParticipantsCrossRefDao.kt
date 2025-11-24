package llc.bokadev.chat.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import llc.bokadev.chat.database.entities.ChatParticipantCrossRef
import llc.bokadev.chat.database.entities.ChatParticipantEntity

@Dao
interface ChatParticipantsCrossRefDao {

    @Upsert
    suspend fun upsertCrossRefs(crossRefs: List<ChatParticipantCrossRef>)

    @Query("SELECT userId FROM chatparticipantcrossref WHERE chatId = :chatId")
    suspend fun getActiveParticipantIdsByChat(chatId: String): List<String>

    @Query("SELECT userId FROM chatparticipantcrossref WHERE chatId = :chatId")
    suspend fun getAllParticipantIdsByChat(chatId: String): List<String>

    @Query(
        """
        UPDATE chatparticipantcrossref
        SET isActive = 0
        WHERE chatId = :chatId AND userId IN (:userIds)
    """
    )
    suspend fun markParticipantsAsInactive(chatId: String, userIds: List<String>)


    @Query(
        """
        UPDATE chatparticipantcrossref
        SET isActive = 1
        WHERE chatId = :chatId AND userId IN (:userIds)
    """
    )
    suspend fun reactivateParticipants(chatId: String, userIds: List<String>)

    @Transaction
    suspend fun syncParticipants(
        chatId: String, participants:
        List<ChatParticipantEntity>
    ) {
        if (participants.isEmpty()) {
            return
        }

        val serverParticipantIds = participants.map { it.userId }.toSet()
        val allLocalParticipants = getAllParticipantIdsByChat(chatId).toSet()
        val activeParticipantIds = getActiveParticipantIdsByChat(chatId).toSet()
        val inactiveParticipantIds = allLocalParticipants - activeParticipantIds

        val participantsToReactivate = serverParticipantIds.intersect(
            inactiveParticipantIds
        )

        val participantsToDeactivate = activeParticipantIds - serverParticipantIds

        reactivateParticipants(chatId, participantsToDeactivate.toList())
        markParticipantsAsInactive(chatId, participantsToDeactivate.toList())

        val completelyNewParticipantIds = serverParticipantIds - allLocalParticipants
        val newCrossRefs = completelyNewParticipantIds.map { userId ->
            ChatParticipantCrossRef(
                chatId = chatId,
                userId = userId,
                isActive = true
            )
        }
        upsertCrossRefs(newCrossRefs)
    }
}