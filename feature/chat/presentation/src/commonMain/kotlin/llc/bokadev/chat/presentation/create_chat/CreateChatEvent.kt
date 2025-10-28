package llc.bokadev.chat.presentation.create_chat

import llc.bokadev.chat.domain.models.Chat

sealed interface CreateChatEvent {
    data class OnChatCreated(val chat: Chat) : CreateChatEvent
}