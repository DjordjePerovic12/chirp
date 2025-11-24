package llc.bokadev.chat.data.mappers

import llc.bokadev.chat.data.dto.ChatDto
import llc.bokadev.chat.database.entities.ChatEntity
import llc.bokadev.chat.database.entities.ChatInfoEntity
import llc.bokadev.chat.database.entities.ChatWithParticipants
import llc.bokadev.chat.database.entities.MessageWithSender
import llc.bokadev.chat.domain.models.Chat
import llc.bokadev.chat.domain.models.ChatInfo
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.domain.models.ChatMessageDeliveryStatus
import llc.bokadev.chat.domain.models.ChatParticipant
import kotlin.time.Instant

typealias DataMessageWithSender = MessageWithSender
typealias DomainMessageWithSender = llc.bokadev.chat.domain.models.MessageWithSender
fun ChatDto.toDomain(): Chat {
    return Chat(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}


fun ChatEntity.toDomain(
    participants: List<ChatParticipant>,
    lastMessage: ChatMessage? = null
): Chat {
    return Chat(
        id = chatId,
        participants = participants,
        Instant.fromEpochMilliseconds(lastActivityAt),
        lastMessage = lastMessage
    )
}

fun ChatWithParticipants.toDomain(): Chat {
    return Chat(
        id = chat.chatId,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.fromEpochMilliseconds(chat.lastActivityAt),
        lastMessage = lastMessage?.toDomain()
    )
}


fun Chat.toEntity(): ChatEntity {
    return ChatEntity(
        chatId = id,
        lastActivityAt = lastActivityAt.toEpochMilliseconds(),
        lastMessage = lastMessage?.content
    )
}

fun DataMessageWithSender.toDomain() : DomainMessageWithSender {
    return DomainMessageWithSender(
        message = message.toDomain(),
        sender = sender.toDomain(),
        deliveryStatus = ChatMessageDeliveryStatus.valueOf(this.message.deliveryStatus)
    )
}

fun ChatInfoEntity.toDomain() : ChatInfo {
    return ChatInfo(
        chat = chat.toDomain(
            participants = this.participants.map { it.toDomain() }
        ),
        messages = messagesWithSenders.map { it.toDomain() }
    )
}