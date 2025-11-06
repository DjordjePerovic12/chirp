package llc.bokadev.chat.presentation.chat_list_detail

import DialogSheetScopedViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneAdaptedValue
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import llc.bokadev.chat.presentation.chat_detail.ChatDetailRoot
import llc.bokadev.chat.presentation.chat_list.ChatListScreenRoot
import llc.bokadev.chat.presentation.create_chat.CreateChatRoot
import llc.bokadev.core.designsystem.theme.extended
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatListDetailsAdaptiveLayout(
    chatListDetailViewModel: ChatListDetailViewModel = koinViewModel(),
    onLogout: () -> Unit,
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

    val detailPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.Detail]
    LaunchedEffect(detailPane, sharedState.selectedChatId) {
        if (detailPane == PaneAdaptedValue.Hidden && sharedState.selectedChatId != null) {
            chatListDetailViewModel.onAction(ChatListDetailsAction.OnChatClick(null))
        }
    }
    ListDetailPaneScaffold(
        directive = scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.extended.surfaceLower),
        listPane = {
            AnimatedPane {
                ChatListScreenRoot(
                    onChatClick = {
                        chatListDetailViewModel.onAction(ChatListDetailsAction.OnChatClick(it.id))
                        scope.launch {
                            scaffoldNavigator.navigateTo(
                                ListDetailPaneScaffoldRole.Detail
                            )
                        }
                    },
                    onConfirmLogoutClick = onLogout,
                    onCreateChatClick = {
                        chatListDetailViewModel.onAction(ChatListDetailsAction.OnCreateChatClick)
                    },
                    onProfileSettingsClick = {
                        chatListDetailViewModel.onAction(ChatListDetailsAction.OnProfileSettingsClick)
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                val listPane = scaffoldNavigator.scaffoldValue[ListDetailPaneScaffoldRole.List]
                ChatDetailRoot(
                    chatId = sharedState.selectedChatId,
                    isDetailsPresent = detailPane == PaneAdaptedValue.Expanded && listPane == PaneAdaptedValue.Expanded,
                    onBack = {
                        scope.launch {
                            if (scaffoldNavigator.canNavigateBack()) {
                                scaffoldNavigator.navigateBack()
                            }
                        }
                    }
                )
            }
        }
    )

    DialogSheetScopedViewModel(
        visible = sharedState.dialogState is DialogState.CreateChat
    ) {
        CreateChatRoot(
            onDismiss = {
                chatListDetailViewModel.onAction(ChatListDetailsAction.OnDismissCurrentDialog)
            },
            onChatCreated = { chat ->
                chatListDetailViewModel.onAction(ChatListDetailsAction.OnDismissCurrentDialog)
                chatListDetailViewModel.onAction(ChatListDetailsAction.OnChatClick(chat.id))
                scope.launch {
                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
            }
        )
    }
}