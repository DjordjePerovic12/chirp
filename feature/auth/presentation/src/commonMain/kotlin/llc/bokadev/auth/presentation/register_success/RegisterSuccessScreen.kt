package llc.bokadev.auth.presentation.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.account_successfully_created
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_sent_to_x
import llc.bokadev.core.designsystem.components.brand.ChirpBrandLogo
import llc.bokadev.core.designsystem.components.brand.ChirpSuccessIcon
import llc.bokadev.core.designsystem.components.buttons.ChirpButton
import llc.bokadev.core.designsystem.components.buttons.ChirpButtonStyle
import llc.bokadev.core.designsystem.layouts.ChirpAdaptiveResultLayout
import llc.bokadev.core.designsystem.layouts.ChirpSimpleSuccessLayout
import llc.bokadev.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterSuccessRoot(
    viewModel: RegisterSuccessViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterSuccessScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    onAction: (RegisterSuccessAction) -> Unit,
) {
    ChirpAdaptiveResultLayout {
        ChirpSimpleSuccessLayout(
            title = stringResource(Res.string.account_successfully_created),
            description = stringResource(
                Res.string.verification_email_sent_to_x,
                state.registeredEmail
            ),
            icon = {
                ChirpSuccessIcon()
            },
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.login),
                    onClick = {
                        onAction(RegisterSuccessAction.OnLoginClick)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.resend_verification_email),
                    onClick = {
                        onAction(RegisterSuccessAction.OnResendEmailVerificationClick)
                    },
                    style = ChirpButtonStyle.SECONDARY,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isResendingVerificationEmail,
                    isLoading = state.isResendingVerificationEmail

                )
            }
        )
    }
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(),
            onAction = {},
        )
    }
}