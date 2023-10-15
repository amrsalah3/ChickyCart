package com.narify.chickycart.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

/**
 * Multi-preview annotation that represents various device sizes to render various devices.
 */
@Preview(
    name = "phone",
    device = "id:pixel_2",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "phone",
    device = "id:pixel_2",
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Preview(
    name = "tablet",
    device = "id:Nexus 10",
    showBackground = true,
    showSystemUi = true
)
@Preview(
    name = "tablet",
    device = "id:Nexus 10",
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
annotation class DevicePreviews
