package llc.bokadev.chat.presentation.chat_list_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import llc.bokadev.chat.domain.chat.ChatConnectionClient
import llc.bokadev.chat.presentation.chat_detail.ChatDetailState

class ChatListDetailViewModel(
    private val connectionClient: ChatConnectionClient
) : ViewModel() {

    private var hasLoadedInitialData = false
    private val _state = MutableStateFlow(ChatListDetailState())
    val state = _state.onStart {
        if (!hasLoadedInitialData) {
            connectionClient.chatMessages.launchIn(viewModelScope)
            hasLoadedInitialData = true
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatListDetailState()
        )


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
