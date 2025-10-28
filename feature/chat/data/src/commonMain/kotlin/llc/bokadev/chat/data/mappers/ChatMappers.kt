package llc.bokadev.chat.data.mappers

import llc.bokadev.chat.data.dto.ChatDto
import llc.bokadev.chat.domain.models.Chat
import kotlin.time.Instant

fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}