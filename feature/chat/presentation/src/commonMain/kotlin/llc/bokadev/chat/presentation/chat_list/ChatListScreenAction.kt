package llc.bokadev.chat.presentation.chat_list

import llc.bokadev.chat.presentation.model.ChatUi

sealed interface ChatListScreenAction {
    data object OnUserAvatarClick: ChatListScreenAction
    data object OnDismissUserMenu: ChatListScreenAction
    data object OnLogoutClick: ChatListScreenAction
    data object OnConfirmLogout: ChatListScreenAction
    data object OnDismissLogout: ChatListScreenAction
    data object OnCreateChatClick: ChatListScreenAction
    data object OnProfileSettingsClick: ChatListScreenAction
    data class OnChatClick(val chat: ChatUi): ChatListScreenAction
}