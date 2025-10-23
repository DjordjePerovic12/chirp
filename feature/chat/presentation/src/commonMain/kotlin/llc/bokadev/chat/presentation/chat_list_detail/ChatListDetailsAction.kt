package llc.bokadev.chat.presentation.chat_list_detail

sealed interface ChatListDetailsAction {
    data class OnChatClick(val chatId: String?) : ChatListDetailsAction
    data object OnProfileSettingsClick : ChatListDetailsAction
    data object OnCreateChatClick : ChatListDetailsAction
    data object OnManageClick : ChatListDetailsAction
    data object OnDismissCurrentDialog : ChatListDetailsAction
}