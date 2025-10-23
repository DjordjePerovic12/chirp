package llc.bokadev.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import llc.bokadev.auth.presentation.navigation.AuthGraphRoutes
import llc.bokadev.auth.presentation.navigation.authGraph
import llc.bokadev.chat.presentation.navigation.ChatGraphRoutes
import llc.bokadev.chat.presentation.navigation.chatGraph

@Composable
fun NavigationRoot(
    navController: NavHostController,
    startDestination: Any
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {
                navController.navigate(ChatGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            }
        )
        chatGraph(navController = navController)
    }
}