package llc.bokadev.chat.presentation.chat_list_detail

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.PaneScaffoldDirective
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import llc.bokadev.core.presentation.util.DeviceConfiguration
import llc.bokadev.core.presentation.util.currentDeviceConfiguration

@Composable
fun createNoSpacingPaneScaffoldDirective(): PaneScaffoldDirective {
    val configuration = currentDeviceConfiguration()
    val windowAdaptiveInfo = currentWindowAdaptiveInfo()

    var maxHorizontalPartitions = when (configuration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_PORTRAIT -> 1

        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> 2
    }

    val verticalPartitionSpaceSize: Dp
    val maxVerticalPartitions: Int

    if (windowAdaptiveInfo.windowPosture.isTabletop) {
        maxVerticalPartitions = 2
        verticalPartitionSpaceSize = 24.dp
    } else {
        maxVerticalPartitions = 1
        verticalPartitionSpaceSize = 0.dp
    }

    return PaneScaffoldDirective(
        maxHorizontalPartitions = maxHorizontalPartitions,
        horizontalPartitionSpacerSize = 0.dp,
        maxVerticalPartitions = maxVerticalPartitions,
        verticalPartitionSpacerSize = verticalPartitionSpaceSize,
        defaultPanePreferredWidth = 360.dp,
        excludedBounds = emptyList()
    )
}