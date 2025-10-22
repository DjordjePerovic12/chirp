package llc.bokadev.auth.presentation.reset_password

import androidx.compose.foundation.text.input.TextFieldState
import llc.bokadev.core.presentation.util.UiText

data class ResetPasswordState(
    val resetPasswordTextFieldState: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val errorText: UiText? = null,
    val isPasswordVisible: Boolean = false,
    val canSubmit: Boolean = false,
    val isResetSuccessful: Boolean = false
)