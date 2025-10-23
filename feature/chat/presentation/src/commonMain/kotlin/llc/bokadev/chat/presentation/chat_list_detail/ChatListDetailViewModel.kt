package llc.bokadev.chat.presentation.chat_list_detail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChatListDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(ChatListDetailState())
    val state = _state.asStateFlow()


    fun onAction(action: ChatListDetailsAction) {
        when (action) {
            is ChatListDetailsAction.OnChatClick -> {
                _state.update {
                    it.copy(
                        selectedChatId = action.chatId
                    )
                }
            }

            ChatListDetailsAction.OnCreateChatClick -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.CreateChat
                    )
                }
            }
            ChatListDetailsAction.OnDismissCurrentDialog -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.Hidden
                    )
                }
            }
            ChatListDetailsAction.OnManageClick -> {
                state.value.selectedChatId?.let { id ->
                    _state.update {
                        it.copy(
                            dialogState = DialogState.ManageChat(id)
                        )
                    }
                }
            }
            ChatListDetailsAction.OnProfileSettingsClick -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.Profile
                    )
                }
            }
        }
    }


}
