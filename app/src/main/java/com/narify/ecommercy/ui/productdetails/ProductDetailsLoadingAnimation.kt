package com.narify.ecommercy.ui.productdetails

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.ecommercy.ui.theme.EcommercyTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.defaultShimmerTheme
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

/**
 * Shimmer animation for loading a product in product details screen.
 */
@Composable
fun LoadingProductDetails(modifier: Modifier = Modifier) {
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

    Column(
        modifier
            .padding(16.dp)
            .shimmer(customShimmer)
    ) {
        Box(
            Modifier
                .height(300.dp)
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.large)
                .background(Color.Gray)
        )

        Box(
            Modifier
                .padding(top = 16.dp, bottom = 4.dp)
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

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingProductDetailsPreview() {
    EcommercyTheme {
        LoadingProductDetails()
    }
}

