package llc.bokadev.chat.presentation.chat_detail

import llc.bokadev.core.presentation.util.UiText

sealed interface ChatDetailEvent {
    data object OnChatLeft: ChatDetailEvent
    data class OnError(val error: UiText): ChatDetailEvent
}