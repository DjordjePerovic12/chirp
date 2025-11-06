package llc.bokadev.chat.presentation.model

import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.presentation.util.UiText

sealed class MessageUi(open val id: String) {
    data class LocalUserMessage(
        override val id: String,
        val content: String,
        val deliveryStatus: ChatMessageDeliveryStatus,
        val formattedSentTime: UiText,
        val isMenuOpen: Boolean
    ) : MessageUi(id = id)

    data class OtherUserMessage(
        override val id: String,
        val content: String,
        val formattedSentTime: UiText,
        val sender: ChatParticipantUi
    ) : MessageUi(id = id)

    data class DateSeparator(
        override val id: String,
        val date: UiText,
    ) : MessageUi(id = id)
}