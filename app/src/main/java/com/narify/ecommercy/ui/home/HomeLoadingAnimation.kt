package com.narify.ecommercy.ui.home

import android.content.res.Configuration
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
 * Shimmer animation for loading products list in home screen.
 */
@Composable
fun LoadingProductsList(modifier: Modifier = Modifier) {
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

    Column(modifier.shimmer(customShimmer)) {
        repeat(10) {
            Surface(
                shape = MaterialTheme.shapes.large,
                shadowElevation = 6.dp,
                modifier = Modifier.padding(8.dp)
            ) {
                Row(
                    Modifier
                        .height(140.dp)
                        .fillMaxWidth()
                ) {
                    Box(
                        Modifier
                            .fillMaxHeight()
                            .weight(1.2f)
                            .background(Color.Gray)
                    )

                    Column(
                        Modifier
                            .padding(16.dp)
                            .weight(2f)
                    ) {
                        Box(
                            Modifier
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
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Box(
                            Modifier
                                .height(16.dp)
                                .fillMaxWidth()
                                .background(Color.Gray)
                        )
                    }
                }
            }
        }
    }
}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoadingProductsListPreview() {
    EcommercyTheme {
        LoadingProductsList()
    }
}
