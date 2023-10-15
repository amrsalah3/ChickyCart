package com.narify.chickycart.ui.home

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarConfig
import com.narify.chickycart.R
import com.narify.chickycart.ui.common.DevicePreviews
import com.narify.chickycart.ui.theme.EcommercyThemePreview
import com.razzaghi.compose_loading_dots.LoadingScaly
import com.razzaghi.compose_loading_dots.core.rememberDotsLoadingController
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

    LazyVerticalGrid(
        columns = GridCells.Adaptive(350.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp),
        userScrollEnabled = false,
        modifier = modifier.shimmer(customShimmer)
    ) {
        items(20) {
            Surface(
                shape = MaterialTheme.shapes.large,
                shadowElevation = 6.dp
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

/**
 * Animation for loading more products in the list.
 */
@Composable
fun LoadingMoreProducts(modifier: Modifier = Modifier) {
    LoadingScaly(
        controller = rememberDotsLoadingController(),
        dotsCount = 3,
        dotsColor = MaterialTheme.colorScheme.primary,
        dotsSize = 10.dp,
        duration = 300,
        easing = LinearEasing,
        modifier = modifier
    )
}

/**
 * Shows error which occurs when trying to load more products in the list.
 */
@Composable
fun ErrorLoadingMoreProducts(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.error_loading_products),
        color = Color.Gray,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@DevicePreviews
@Composable
fun LoadingProductsListPreview() {
    EcommercyThemePreview {
        LoadingProductsList()
    }
}

@Preview
@Composable
fun LoadingMoreProductsPreview() {
    EcommercyThemePreview {
        LoadingMoreProducts()
    }
}

@Preview
@Composable
fun ErrorLoadingMoreProductsPreview() {
    EcommercyThemePreview {
        ErrorLoadingMoreProducts()
    }
}
