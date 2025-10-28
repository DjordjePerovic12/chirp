package llc.bokadev.chat.data.mappers

import llc.bokadev.chat.data.dto.ChatParticipantDto
import llc.bokadev.chat.domain.models.ChatParticipant

fun ChatParticipantDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl
    )
}