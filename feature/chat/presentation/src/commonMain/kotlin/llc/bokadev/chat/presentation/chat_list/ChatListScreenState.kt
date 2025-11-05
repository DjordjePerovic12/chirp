package llc.bokadev.chat.presentation.chat_list

import llc.bokadev.chat.domain.models.ChatParticipant
import llc.bokadev.chat.presentation.model.ChatUi
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.presentation.util.UiText

data class ChatListScreenState(
    val chats: List<ChatUi> = emptyList(),
    val error: UiText? = null,
    val localParticipant: ChatParticipantUi? = null,
    val isUserMenuOpen: Boolean = false,
    val showLogoutConfirmation: Boolean = false,
    val selectedChatId: String? = null,
    val isLoading: Boolean = false
)