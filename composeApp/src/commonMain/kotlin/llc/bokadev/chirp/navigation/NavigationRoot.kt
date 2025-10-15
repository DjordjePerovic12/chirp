package llc.bokadev.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import llc.bokadev.auth.presentation.navigation.AuthGraphRoutes
import llc.bokadev.auth.presentation.navigation.authGraph

@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph
    ) {
        authGraph(
            navController = navController,
            onLoginSuccess = {

            }
        )
    }
}