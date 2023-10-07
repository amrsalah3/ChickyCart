package com.narify.ecommercy.ui.checkout

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.narify.ecommercy.R
import com.narify.ecommercy.ui.common.DevicePreviews
import com.narify.ecommercy.ui.theme.EcommercyThemePreview
import kotlinx.coroutines.delay

/**
 * Loading animation for placing an order.
 */
@Composable
fun PlacingOrderLoading(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colorScheme.primary,
    animationDelay: Int = 1200,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        // 3 circles
        val circles = listOf(
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            },
            remember {
                Animatable(initialValue = 0f)
            }
        )

        circles.forEachIndexed { index, animatable ->
            LaunchedEffect(Unit) {
                // Use coroutine delay to sync animations
                // divide the animation delay by number of circles - 1
                // as the first circle appears immediately without delay
                val timeDelay = animationDelay / (circles.size - 1)
                delay(timeMillis = timeDelay.toLong() * index)

                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = animationDelay,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Restart
                    )
                )
            }
        }

        // Outer circle
        Box(
            Modifier
                .size(size = 200.dp)
                .background(color = Color.Transparent)
        ) {
            // animating circles
            circles.forEachIndexed { index, animatable ->
                Box(
                    Modifier
                        .scale(scale = animatable.value)
                        .size(size = 200.dp)
                        .clip(shape = CircleShape)
                        .background(color = circleColor.copy(alpha = (1 - animatable.value)))
                )
            }
        }

        Text(
            text = stringResource(R.string.order_result_loading),
            fontWeight = FontWeight.Bold,
            color = circleColor,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}


@DevicePreviews
@Composable
fun PlacingOrderLoadingPreview() {
    EcommercyThemePreview {
        PlacingOrderLoading()
    }
}
