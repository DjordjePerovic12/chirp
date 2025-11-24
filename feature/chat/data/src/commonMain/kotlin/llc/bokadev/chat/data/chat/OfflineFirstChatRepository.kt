package llc.bokadev.chat.data.chat

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import llc.bokadev.chat.data.mappers.toDomain
import llc.bokadev.chat.data.mappers.toEntity
import llc.bokadev.chat.data.mappers.toLastMessageView
import llc.bokadev.chat.database.ChirpChatDatabase
import llc.bokadev.chat.database.entities.ChatWithParticipants
import llc.bokadev.chat.domain.chat.ChatRepository
import llc.bokadev.chat.domain.chat.ChatService
import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.chat.domain.models.ChatInfo
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.EmptyResult
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.asEmptyResult
import llc.bokadev.core.domain.util.onFailure
import llc.bokadev.core.domain.util.onSuccess

class OfflineFirstChatRepository(
    private val chatService: ChatService,
    private val db: ChirpChatDatabase
) : ChatRepository {
    override fun getChats(): Flow<List<Chat>> {
        return db.chatDao.getChatsWithActiveParticipants()
            .map { chatWithParticipantsList ->
                chatWithParticipantsList
                    .map { it.toDomain() }
            }
    }

    override fun getChatInfoById(chatId: String): Flow<ChatInfo> {
        return db.chatDao.getChatInfoById(chatId)
            .filterNotNull()
            .map { it.toDomain() }
    }

    override suspend fun fetchChats(): Result<List<Chat>, DataError.Remote> {
        return chatService
            .getChats()
            .onSuccess { chats ->
                val chatsWithParticipants = chats.map { chat ->
                    ChatWithParticipants(
                        chat = chat.toEntity(),
                        participants = chat.participants.map { it.toEntity() },
                        lastMessage = chat.lastMessage?.toLastMessageView()
                    )

                }

                db.chatDao.upsertChatsWithParticipantsAndCrossRefs(
                    chats = chatsWithParticipants,
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao,
                    messageDao = db.chatMessageDao
                )
            }
    }


    override suspend fun fetchChatById(chatId: String): EmptyResult<DataError.Remote> {
        return chatService.getChatById(chatId)
            .onSuccess { chat ->
                db.chatDao.upsertChatWithParticipantsAndCrossRefs(
                    chat = chat.toEntity(),
                    participants = chat.participants.map { it.toEntity() },
                    participantDao = db.chatParticipantDao,
                    crossRefDao = db.chatParticipantsCrossRefDao
                )
            }.asEmptyResult()
    }
}