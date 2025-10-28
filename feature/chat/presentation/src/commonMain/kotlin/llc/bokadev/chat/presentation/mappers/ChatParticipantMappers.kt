package llc.bokadev.chat.presentation.mappers

import llc.bokadev.chat.domain.models.ChatParticipant
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi() : ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}