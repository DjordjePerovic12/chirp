package llc.bokadev.chat.presentation.model

import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi

data class ChatUi(
    val id: String,
    val localParticipant: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage?,
    val lastMessageSenderUsername: String?

)
