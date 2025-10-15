package llc.bokadev.chirp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import chirp.composeapp.generated.resources.Res
import chirp.composeapp.generated.resources.compose_multiplatform
import llc.bokadev.auth.presentation.register.RegisterRoot
import llc.bokadev.auth.presentation.register_success.RegisterSuccessRoot
import llc.bokadev.auth.presentation.register_success.RegisterSuccessScreen
import llc.bokadev.core.designsystem.theme.ChirpTheme

@Composable
@Preview
fun App() {
    ChirpTheme {
        RegisterSuccessRoot(

        )
    }
}