package llc.bokadev.auth.presentation.login

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.error_invalid_credentials
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import llc.bokadev.auth.domain.EmailValidator
import llc.bokadev.core.domain.auth.AuthService
import llc.bokadev.core.domain.util.DataError
import llc.bokadev.core.domain.util.onFailure
import llc.bokadev.core.domain.util.onSuccess
import llc.bokadev.core.presentation.util.UiText
import llc.bokadev.core.presentation.util.toUiText

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val eventChannel = Channel<LoginEvent>()
    val events = eventChannel.receiveAsFlow()

    private val isEmailValidFlow = snapshotFlow { state.value.emailTextFieldState.text.toString() }
        .map { email -> EmailValidator.validate(email) }
        .distinctUntilChanged()

    private val isPasswordNotBlankFlow =
        snapshotFlow { state.value.passwordTextFieldState.text.toString() }
            .map { it.isNotBlank() }
            .distinctUntilChanged()


    private val _state = MutableStateFlow(LoginState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                observeTextStates()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = LoginState()
        )

    private val isLoggingInFlow = state
        .map { it.isLoggingIn }

    fun onAction(action: LoginAction) {
        when (action) {
            LoginAction.OnLoginClick -> login()
            LoginAction.OnTogglePasswordVisibility -> {
                _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            }

            else -> Unit
        }
    }


    private fun observeTextStates() {
        combine(
            isEmailValidFlow,
            isPasswordNotBlankFlow,
            isLoggingInFlow
        ) { isEmailValid, isPasswordNotBlank, isLoggingIn ->
            _state.update {
                it.copy(
                    canLogin = !isLoggingIn && isEmailValid && isPasswordNotBlank
                )
            }
        }.launchIn(viewModelScope)
    }

    private fun login() {
        if (!state.value.canLogin) {
            return
        }
        viewModelScope.launch {
            _state.update {
                it.copy(isLoggingIn = true)
            }

            val email = state.value.emailTextFieldState.text.toString()
            val password = state.value.passwordTextFieldState.text.toString()

            authService
                .login(
                    email = email,
                    password = password
                )
                .onSuccess { authInfo ->
                    _state.update {
                        it.copy(isLoggingIn = false)
                    }
                    eventChannel.send(LoginEvent.Success)
                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        DataError.Remote.UNAUTHORIZED -> UiText.Resource(Res.string.error_invalid_credentials)
                        DataError.Remote.FORBIDDEN -> UiText.Resource(Res.string.error_invalid_credentials)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(isLoggingIn = false, error = errorMessage)
                    }
                }
        }

    }

}