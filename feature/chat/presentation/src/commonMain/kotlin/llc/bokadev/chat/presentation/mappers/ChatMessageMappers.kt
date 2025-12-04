package llc.bokadev.chat.presentation.mappers

import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.MessageWithSender
import llc.bokadev.chat.presentation.model.MessageUi
import llc.bokadev.chat.presentation.util.DateUtils

fun MessageWithSender.toUi(localUserId: String): MessageUi {
    val isFromLocalUser = this.sender.userId == localUserId
    return if (isFromLocalUser) {
        MessageUi.LocalUserMessage(
            id = message.id,
            content = message.content,
            deliveryStatus = message.deliveryStatus,
            isMenuOpen = false,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt)
        )
    } else {
        MessageUi.OtherUserMessage(
            id = message.id,
            content = message.content,
            formattedSentTime = DateUtils.formatMessageTime(instant = message.createdAt),
            sender = sender.toUi()
        )
    }
}