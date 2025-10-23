package llc.bokadev.chat.presentation.chat_list_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import llc.bokadev.core.designsystem.theme.extended
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatListDetailsAdaptiveLayout(
    chatListDetailViewModel: ChatListDetailViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val sharedState by chatListDetailViewModel.state.collectAsStateWithLifecycle()
    val scaffoldDirective = createNoSpacingPaneScaffoldDirective()
    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator(
        scaffoldDirective = scaffoldDirective
    )
    val scope = rememberCoroutineScope()

    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }
    ListDetailPaneScaffold(
        directive = scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.extended.surfaceLower),
        listPane = {
            AnimatedPane {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(100) { chatIndex ->
                        Text(
                            text = "CHAT $chatIndex",
                            modifier = Modifier.clickable {
                                chatListDetailViewModel.onAction(
                                    ChatListDetailsAction.OnChatClick(
                                        chatIndex.toString()
                                    )
                                )
                                scope.launch {
                                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                }
                            }
                                .padding(16.dp)
                        )
                    }
                }
            }
        },
        detailPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    sharedState.selectedChatId?.let {
                        Text(
                            text = it
                        )
                    }
                }
            }
        }
    )
}