package llc.bokadev.core.designsystem.preview

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import llc.bokadev.core.designsystem.components.brand.ChirpBrandLogo
import llc.bokadev.core.designsystem.layouts.ChirpAdaptiveFormLayout
import llc.bokadev.core.designsystem.theme.ChirpTheme

@Composable
@PreviewLightDark
@PreviewScreenSizes
@Preview (
    device = Devices.NEXUS_10
)
fun ChirpAdaptiveFromLayoutPreview() {
    ChirpTheme {
        ChirpAdaptiveFormLayout(
            headerText = "Welcome to Chrip!",
            errorText = "Login failed!",
            logo = {
                ChirpBrandLogo()
            },
            formContent = {
                Text("sampe form data")
                Text("sampe form data")
                Text("sampe form data")
            }
        )
    }
}