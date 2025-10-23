package llc.bokadev.core.designsystem.components.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import llc.bokadev.core.designsystem.theme.ChirpTheme
import llc.bokadev.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpChatBubble(
    modifier: Modifier = Modifier,
    messageContent: String,
    sender: String,
    formattedDateTime: String,
    color: Color = MaterialTheme.colorScheme.extended.surfaceHigher,
    messageStatus: @Composable (() -> Unit)? = null,
    triangleSize: Dp = 16.dp,
    trianglePosition: TrianglePosition,
    onLongClick: (() -> Unit)? = null
) {
    val padding = 12.dp
    Column(
        modifier = modifier
            .then(
                if (onLongClick != null) {
                    Modifier.combinedClickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(
                            color = MaterialTheme.colorScheme.extended.surfaceOutline
                        ),
                        onLongClick = onLongClick,
                        onClick = {}
                    )
                } else Modifier
            )
            .clip(
                ChirpChatBubbleShape(
                    triangleSize = triangleSize,
                    trianglePosition = trianglePosition
                )
            )
            .background(color)
            .padding(
                start = if (trianglePosition == TrianglePosition.LEFT) {
                    padding + triangleSize
                } else padding,
                end = if (trianglePosition == TrianglePosition.RIGHT) {
                    padding + triangleSize
                } else padding,
                top = padding,
                bottom = padding,
            ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sender,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = formattedDateTime,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.extended.textSecondary
            )
        }

        Text(
            text = messageContent,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            modifier = Modifier.fillMaxWidth()
        )
        messageStatus?.invoke()
    }
}

@Composable
@Preview
fun ChirpChatBubbleLeftPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpChatBubble(
            messageContent = "Hello world, this is a longer message that hopefully spans over multiple" +
                    "linse so we can see how it looks",
            sender = "Djordje",
            formattedDateTime = "Friday 2:20pm",
            trianglePosition = TrianglePosition.LEFT,
            modifier = Modifier.widthIn(max = 250.dp),
            color = MaterialTheme.colorScheme.extended.accentGreen

        )
    }
}

@Composable
@Preview
fun ChirpChatBubbleRightPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpChatBubble(
            messageContent = "Hello world, this is a longer message that hopefully spans over multiple" +
                    "linse so we can see how it looks",
            sender = "Djordje",
            formattedDateTime = "Friday 2:20pm",
            trianglePosition = TrianglePosition.RIGHT,
            modifier = Modifier.widthIn(max = 250.dp),
            color = MaterialTheme.colorScheme.extended.accentYellow

        )
    }
}