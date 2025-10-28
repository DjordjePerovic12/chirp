package llc.bokadev.chat.domain.chat

import llc.bokadev.chat.domain.models.ChatParticipant
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query: String
    ): Result<ChatParticipant, DataError.Remote>
}