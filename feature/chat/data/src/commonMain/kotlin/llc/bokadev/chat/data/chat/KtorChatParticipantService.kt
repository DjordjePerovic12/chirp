package llc.bokadev.chat.data.chat

import io.ktor.client.HttpClient
import llc.bokadev.chat.data.dto.ChatParticipantDto
import llc.bokadev.chat.data.mappers.toDomain
import llc.bokadev.chat.domain.chat.ChatParticipantService
import llc.bokadev.chat.domain.models.ChatParticipant
import llc.bokadev.core.data.networking.get
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result
import llc.bokadev.core.domain.util.map

class KtorChatParticipantService(
    private val httpClient: HttpClient
) : ChatParticipantService {
    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantDto>(
            route = "/participants",
            queryParams = mapOf(
                "query" to query
            )
        ).map { it.toDomain() }
    }
}