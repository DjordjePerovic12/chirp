package llc.bokadev.chat.data.chat

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import llc.bokadev.chat.data.dto.ChatDto
import llc.bokadev.chat.data.dto.request.CreateChatRequest
import llc.bokadev.chat.data.mappers.toDomain
import llc.bokadev.chat.domain.chat.ChatService
import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.core.data.networking.get
import llc.bokadev.core.data.networking.post
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.map

class KtorChatService(
    private val httpClient: HttpClient
) : ChatService {
    override suspend fun createChat(otherUserIds: List<String>): Result<Chat, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chat",
            body = CreateChatRequest(
                otherUserIds = otherUserIds
            )
        ).map { it.toDomain() }
    }

    override suspend fun getChats(): Result<List<Chat>, DataError.Remote> {
        return httpClient.get<List<ChatDto>>(
            route = "/chat"
        ).map { chatDtos ->
            chatDtos.map { it.toDomain() }
        }
    }
}