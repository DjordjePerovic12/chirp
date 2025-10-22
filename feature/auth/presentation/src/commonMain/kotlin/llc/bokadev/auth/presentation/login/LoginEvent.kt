package llc.bokadev.auth.presentation.login

sealed interface LoginEvent {
    data object Success: LoginEvent
}