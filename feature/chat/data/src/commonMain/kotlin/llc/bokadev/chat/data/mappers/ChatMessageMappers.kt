package llc.bokadev.chat.data.mappers

import llc.bokadev.chat.data.dto.ChatMessageDto
import llc.bokadev.chat.domain.models.ChatMessage
import kotlin.time.Instant

fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId
    )
}