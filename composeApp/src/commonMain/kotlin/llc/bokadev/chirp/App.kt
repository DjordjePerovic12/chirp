package llc.bokadev.chirp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import llc.bokadev.auth.presentation.navigation.AuthGraphRoutes
import llc.bokadev.chat.presentation.navigation.ChatGraphRoutes
import llc.bokadev.chirp.navigation.DeepLinkListener
import llc.bokadev.chirp.navigation.NavigationRoot
import llc.bokadev.core.designsystem.theme.ChirpTheme
import llc.bokadev.core.presentation.util.ObserveAsEvents
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    onAuthenticationChecked: () -> Unit = {},
    viewModel: MainViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    DeepLinkListener(navController)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isCheckingAuth) {
        if (!state.isCheckingAuth) {
            onAuthenticationChecked()
        }
    }

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is MainEvent.OnSessionExpired -> {
                navController.navigate(
                    AuthGraphRoutes.Graph
                ) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = false
                    }
                }
            }
        }

    }

    ChirpTheme {
        if (!state.isCheckingAuth) {
            NavigationRoot(
                navController = navController,
                startDestination = if (state.isLoggedIn) {
                    ChatGraphRoutes.Graph
                } else {
                    AuthGraphRoutes.Graph
                }
            )
        }
    }
}