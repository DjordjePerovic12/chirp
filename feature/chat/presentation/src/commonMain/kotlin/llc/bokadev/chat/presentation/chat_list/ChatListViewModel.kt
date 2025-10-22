package llc.bokadev.chat.presentation.chat_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import llc.bokadev.core.domain.auth.SessionStorage

class ChatListViewModel(
    private val sessionStorage: SessionStorage
) : ViewModel() {

}