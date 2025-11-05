package llc.bokadev.chat.presentation.chat_list

import llc.bokadev.chat.presentation.model.ChatUi

sealed interface ChatListAction {
    data object OnUserAvatarClick: ChatListAction
    data object OnDismissUserMenu: ChatListAction
    data object OnLogoutClick: ChatListAction
    data object OnConfirmLogout: ChatListAction
    data object OnDismissLogout: ChatListAction
    data object OnCreateChatClick: ChatListAction
    data object OnProfileSettingsClick: ChatListAction
    data class OnChatClick(val chat: ChatUi): ChatListAction
}