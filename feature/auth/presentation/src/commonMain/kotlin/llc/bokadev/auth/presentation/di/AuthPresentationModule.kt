package llc.bokadev.auth.presentation.di

import llc.bokadev.auth.presentation.email_verification.EmailVerificationViewModel
import llc.bokadev.auth.presentation.register.RegisterViewModel
import llc.bokadev.auth.presentation.register_success.RegisterSuccessViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val authPresentationModule = module {
    viewModelOf(::RegisterViewModel)
    viewModelOf(::RegisterSuccessViewModel)
    viewModelOf(::EmailVerificationViewModel)
}