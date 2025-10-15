package llc.bokadev.auth.presentation.register_success

sealed interface RegisterSuccessAction {
    data object OnLoginClick : RegisterSuccessAction
    data object OnResendEmailVerificationClick: RegisterSuccessAction
}