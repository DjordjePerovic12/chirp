package llc.bokadev.auth.presentation.di

import llc.bokadev.auth.presentation.email_verification.EmailVerificationViewModel
import llc.bokadev.auth.presentation.forgot_password.ForgotPasswordViewModel
import llc.bokadev.auth.presentation.login.LoginViewModel
import llc.bokadev.auth.presentation.register.RegisterViewModel
import llc.bokadev.auth.presentation.register_success.RegisterSuccessViewModel
import llc.bokadev.auth.presentation.reset_password.ResetPasswordViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ForgotPasswordViewModel)
    viewModelOf(::ResetPasswordViewModel)
}