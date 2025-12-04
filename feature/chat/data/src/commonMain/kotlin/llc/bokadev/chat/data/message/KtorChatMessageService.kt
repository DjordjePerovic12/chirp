package llc.bokadev.chat.data.message

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import llc.bokadev.chat.data.dto.ChatMessageDto
import llc.bokadev.chat.data.mappers.toDomain
import llc.bokadev.chat.domain.message.ChatMessageService
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.core.data.networking.get
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.map

class KtorChatMessageService(
    private val httpClient: HttpClient
) : ChatMessageService {
    override suspend fun fetchMessages(
        chatId: String,
        before: String?
    ): Result<List<ChatMessage>, DataError.Remote> {
        return httpClient.get<List<ChatMessageDto>>(
            route = "/chat/$chatId/messages",
            queryParams = buildMap {
                this["pageSize"] = ChatMessageConstants.PAGE_SIZE
                if (before != null) {
                    this["before"] = before
                }
            }
        ).map { it.map { it.toDomain() } }
    }
}