package llc.bokadev.chat.presentation.chat_detail

import androidx.compose.foundation.text.input.TextFieldState
import llc.bokadev.chat.domain.models.ConnectionState
import llc.bokadev.chat.presentation.model.ChatUi
import llc.bokadev.chat.presentation.model.MessageUi
import llc.bokadev.core.presentation.util.UiText

data class ChatDetailState(
    val chatUi: ChatUi? = null,
    val isLoading: Boolean = false,
    val messages: List<MessageUi> = emptyList(),
    val error: UiText? = null,
    val messageTextFieldState: TextFieldState = TextFieldState(),
    val canSendMessage: Boolean = false,
    val isPaginationLoading: Boolean = false,
    val paginationError: UiText? = null,
    val endReached: Boolean = false,
    val isChatOptionsOpen: Boolean = false,
    val isNearBottom: Boolean = false,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val bannerState: BannerState? = BannerState()

)


data class BannerState(
    val formattedDate: UiText? = null,
    val isVisible: Boolean = false
)