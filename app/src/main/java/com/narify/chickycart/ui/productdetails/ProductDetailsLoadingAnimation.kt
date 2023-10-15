package com.narify.chickycart.ui.productdetails

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.chickycart.ui.common.DevicePreviews
import com.narify.chickycart.ui.theme.ChickyCartThemePreview
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

/**
 * Shimmer animation for loading a product in product details screen.
 */
@Composable
fun LoadingProductDetails() {
    val customShimmer = rememberShimmer(
        shimmerBounds = ShimmerBounds.View,
        theme = defaultShimmerTheme.copy(
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    delayMillis = 500,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
        )
    )

    BoxWithConstraints(
        Modifier
            .padding(16.dp)
            .shimmer(customShimmer)
    ) {
        if (maxWidth < 450.dp) {
            Column(Modifier.fillMaxSize()) {
                ImagesSection(
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1F)
                )
                DetailsSection(Modifier.padding(vertical = 16.dp))
            }
        } else {
            Row(Modifier.fillMaxSize()) {
                ImagesSection(
                    Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5F)
                )
                DetailsSection(Modifier.padding(start = 16.dp))
            }
        }
    }
}

@Composable
private fun ImagesSection(modifier: Modifier = Modifier) {
    Box(
        modifier
            .clip(MaterialTheme.shapes.large)
            .background(Color.Gray)
    )
}

@Composable
private fun DetailsSection(modifier: Modifier = Modifier) {
    Column(modifier) {
        Box(
            Modifier
                .padding(bottom = 4.dp)
                .height(16.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        Box(
            Modifier
                .padding(end = 64.dp)
                .height(16.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )

        RatingBar(
            value = 0f,
            config = RatingBarConfig()
                .inactiveColor(Color.Gray)
                .size(20.dp),
            onValueChange = {},
            onRatingChanged = {},
            modifier = Modifier.padding(vertical = 16.dp)
        )

        repeat(15) {
            Box(
                Modifier
                    .padding(bottom = 4.dp)
                    .height(8.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
        }

        Box(
            Modifier
                .padding(bottom = 4.dp, end = 100.dp)
                .height(8.dp)
                .fillMaxWidth()
                .background(Color.Gray)
        )
    }
}

@DevicePreviews
@Composable
fun LoadingProductDetailsPreview() {
    ChickyCartThemePreview {
        LoadingProductDetails()
    }
}
