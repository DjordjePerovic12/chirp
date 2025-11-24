package llc.bokadev.chat.presentation.mappers

import llc.bokadev.chat.domain.models.ChatParticipant
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.domain.auth.User

fun ChatParticipant.toUi() : ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        initials = initials,
        imageUrl = profilePictureUrl
    )
}

fun User.toUi() : ChatParticipantUi {
    return ChatParticipantUi(
        id = id,
        username = username,
        initials = username.take(2).uppercase(),
        imageUrl = profilePictureUrl
    )
}