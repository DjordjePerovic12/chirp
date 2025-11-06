package llc.bokadev.chat.presentation.util

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import llc.bokadev.core.designsystem.theme.extended

@Composable
fun getChatBubbleColorForUser(userId: String): Color {
    val colorPool = with(MaterialTheme.colorScheme.extended) {
        listOf(
            cakeViolet,
            cakeGreen,
            cakePink,
            cakeOrange,
            cakeYellow,
            cakePurple,
            cakeRed,
            cakeMint
        )
    }

    val index = userId.hashCode().toUInt() % colorPool.size.toUInt()

    return colorPool[index.toInt()]
}