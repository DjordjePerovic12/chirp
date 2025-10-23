package llc.bokadev.chat.domain.models

data class ChatInfo(
    val chat: Chat,
    val messages: List<MessageWithSender>
)
