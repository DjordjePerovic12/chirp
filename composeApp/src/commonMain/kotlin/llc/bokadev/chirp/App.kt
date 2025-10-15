package llc.bokadev.chirp

import androidx.compose.runtime.Composable
import llc.bokadev.chirp.navigation.NavigationRoot
import llc.bokadev.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    ChirpTheme {
        NavigationRoot()
    }
}