package llc.bokadev.chat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import llc.bokadev.chat.database.dao.ChatDao
import llc.bokadev.chat.database.dao.ChatMessageDao
import llc.bokadev.chat.database.dao.ChatParticipantDao
import llc.bokadev.chat.database.dao.ChatParticipantsCrossRefDao
import llc.bokadev.chat.database.entities.ChatEntity
import llc.bokadev.chat.database.entities.ChatMessageEntity
import llc.bokadev.chat.database.entities.ChatParticipantCrossRef
import llc.bokadev.chat.database.entities.ChatParticipantEntity
import llc.bokadev.chat.database.view.LastMessageView

@Database(
    entities = [
        ChatEntity::class,
        ChatParticipantEntity::class,
        ChatMessageEntity::class,
        ChatParticipantCrossRef::class
    ],
    views = [
        LastMessageView::class
    ],
    version = 1
)

abstract class ChirpChatDatabase : RoomDatabase() {
    abstract val chaDao: ChatDao
    abstract val chatParticipantDao: ChatParticipantDao
    abstract val chatMessageDao: ChatMessageDao
    abstract val chatParticipantsCrossRefDao: ChatParticipantsCrossRefDao

    companion object {
        const val DB_NAME = "chirp.db"
    }
}