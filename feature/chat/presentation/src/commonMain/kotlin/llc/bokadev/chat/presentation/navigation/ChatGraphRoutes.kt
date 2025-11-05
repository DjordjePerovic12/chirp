package llc.bokadev.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable
import llc.bokadev.chat.presentation.chat_list_detail.ChatListDetailsAdaptiveLayout

sealed interface ChatGraphRoutes {

    @Serializable
    data object Graph : ChatGraphRoutes

    @Serializable
    data object ChatListDetailRoute : ChatGraphRoutes
}

fun NavGraphBuilder.chatGraph(
    navController: NavController
) {
    navigation<ChatGraphRoutes.Graph>(
        startDestination = ChatGraphRoutes.ChatListDetailRoute
    ) {
        composable<ChatGraphRoutes.ChatListDetailRoute> {
            ChatListDetailsAdaptiveLayout(
                onLogout = {

                }
            )
        }
    }
}