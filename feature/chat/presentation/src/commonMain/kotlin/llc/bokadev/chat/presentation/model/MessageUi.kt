package llc.bokadev.chat.presentation.model

import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.presentation.util.UiText

sealed interface MessageUi {
    data class LocalUserMessage(
        val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val formattedSentTime: UiText,
        val isMenuOpen: Boolean
    ) : MessageUi

    data class OtherUserMessage(
        val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ) : MessageUi

    data class DateSeparator(
        val id: String,
        val date: UiText,
    ) : MessageUi
}