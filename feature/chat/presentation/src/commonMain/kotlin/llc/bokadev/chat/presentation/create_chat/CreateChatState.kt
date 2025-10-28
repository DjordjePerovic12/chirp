package llc.bokadev.chat.presentation.create_chat

import androidx.compose.foundation.text.input.TextFieldState
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.presentation.util.UiText

data class CreateChatState(
    val queryTextState: TextFieldState = TextFieldState(),
    val selectedChatParticipants: List<ChatParticipantUi> = emptyList(),
    val isSearchingParticipants: Boolean = false,
    val isLoadingParticipants: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipantUi? = null,
    val searchError: UiText? = null,
    val isCreatingChat: Boolean = false
)