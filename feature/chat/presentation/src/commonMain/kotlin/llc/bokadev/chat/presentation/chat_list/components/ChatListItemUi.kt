package llc.bokadev.chat.presentation.chat_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.group_chat
import chirp.feature.chat.presentation.generated.resources.you
import llc.bokadev.chat.domain.models.ChatMessage
import llc.bokadev.chat.presentation.components.ChatItemHeaderRow
import llc.bokadev.chat.presentation.model.ChatUi
import llc.bokadev.core.designsystem.components.avatar.ChatParticipantUi
import llc.bokadev.core.designsystem.components.avatar.ChirpStackedAvatars
import llc.bokadev.core.designsystem.theme.ChirpTheme
import llc.bokadev.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Instant

@Composable
fun ChatListItemUI(
    chat: ChatUi,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val isGroupChat = chat.otherParticipants.size > 1
    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .background(
                if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.extended.surfaceLower
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ChatItemHeaderRow(
                chat = chat,
                isGroupChat = isGroupChat,
                modifier = Modifier.fillMaxWidth()
            )
            if (chat.lastMessage != null) {
                val previewMessage = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.extended.textSecondary
                        )
                    ) {
                        append(chat.lastMessageSenderUsername + ":")
                    }
                    append(chat.lastMessage.content)
                }
                Text(
                    text = previewMessage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.extended.textSecondary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Box(
            modifier = Modifier
                .alpha(if (isSelected) 1f else 0f)
                .background(MaterialTheme.colorScheme.primary)
                .width(4.dp)
                .fillMaxHeight()
        )
    }
}


@Composable
@Preview
fun ChatListItemUiPreview() {
    ChirpTheme(
        darkTheme = true
    ) {
        ChatListItemUI(
            chat = ChatUi(
                id = "1",
                localParticipant = ChatParticipantUi(
                    id = "1",
                    username = "Djordje",
                    initials = "DJP"
                ),
                otherParticipants = listOf(
                    ChatParticipantUi(
                        id = "2",
                        username = "Marie",
                        initials = "MM"
                    ),
                    ChatParticipantUi(
                        id = "3",
                        username = "Djoimba",
                        initials = "DJ"
                    ),
                    ChatParticipantUi(
                        id = "4",
                        username = "Mao",
                        initials = "MA"
                    ),
                ),
                lastMessage = ChatMessage(
                    id = "1",
                    chatId = "1",
                    content = "Donesite mi plazmu, i cokoladu, i igracke",
                    createdAt = Instant.fromEpochMilliseconds(10000000),
                    senderId = "2"
                ),
                lastMessageSenderUsername = "Djimba"
            ),
            isSelected = false,
        )
    }
}