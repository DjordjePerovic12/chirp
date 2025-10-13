package llc.bokadev.core.designsystem.preview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import llc.bokadev.core.designsystem.layouts.ChirpAdaptiveResultLayout
import llc.bokadev.core.designsystem.theme.ChirpTheme


@Composable
@PreviewLightDark
@PreviewScreenSizes
@Preview
fun ChirpAdapativeResultLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveResultLayout(
            content = {
                Text("Hello")
                Text("Hello")
                Text("Hello")
            }
        )
    }
}