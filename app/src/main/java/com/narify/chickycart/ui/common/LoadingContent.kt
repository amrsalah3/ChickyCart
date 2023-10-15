package com.narify.chickycart.ui.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.narify.chickycart.ui.theme.EcommercyThemePreview
import com.razzaghi.compose_loading_dots.LoadingWavy
import com.razzaghi.compose_loading_dots.core.rememberDotsLoadingController

/**
 * Loading animation for general content.
 */
@Composable
fun LoadingContent(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize()) {
        LoadingWavy(
            controller = rememberDotsLoadingController(),
            dotsCount = 3,
            dotsColor = MaterialTheme.colorScheme.primary,
            dotsSize = 20.dp,
            duration = 300,
            easing = LinearEasing,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@DevicePreviews
@Composable
fun LoadingContentPreview() {
    EcommercyThemePreview {
        LoadingContent()
    }
}

