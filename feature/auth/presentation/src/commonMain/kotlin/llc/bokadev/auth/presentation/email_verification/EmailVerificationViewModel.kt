package llc.bokadev.auth.presentation.email_verification

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.core.domain.auth.AuthService
import llc.bokadev.core.domain.util.onFailure
import llc.bokadev.core.domain.util.onSuccess

class EmailVerificationViewModel(
    private val authService: AuthService,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val token = savedStateHandle.get<String>("token")
    private val _state = MutableStateFlow(EmailVerificationState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                verifyEmail()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = EmailVerificationState()
        )

    //NO-OP: Actions are purely for navigation
    fun onAction(action: EmailVerificationAction) = Unit

    private fun verifyEmail() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isVerified = true
                )
            }

            authService
                .verifyEmail(token ?: "Invalid token")
                .onSuccess {
                    _state.update {
                        it.copy(
                            isVerified = true,
                            isVerifying = false
                        )
                    }
                }
                .onFailure { _ ->
                    _state.update {
                        it.copy(
                            isVerified = false,
                            isVerifying = false
                        )
                    }
                }
        }
    }

}