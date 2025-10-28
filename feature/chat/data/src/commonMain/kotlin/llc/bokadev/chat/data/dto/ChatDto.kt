package llc.bokadev.chat.data.dto

import kotlinx.serialization.Serializable
import llc.bokadev.chat.domain.models.ChatMessage

@Serializable
data class ChatDto(
    val id: String,
    val participants: List<ChatParticipantDto>,
    val lastActivityAt: String,
    val lastMessage: ChatMessageDto?
)
