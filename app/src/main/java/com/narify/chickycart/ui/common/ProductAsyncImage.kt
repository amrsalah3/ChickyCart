package com.narify.chickycart.ui.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.narify.chickycart.R

@Composable
fun ProductAsyncImage(imageUrl: String, modifier: Modifier = Modifier) {
    val imageStatePainter = ColorPainter(MaterialTheme.colorScheme.secondary)
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(500)
            .build(),
        placeholder = imageStatePainter,
        fallback = imageStatePainter,
        error = imageStatePainter,
        contentDescription = stringResource(R.string.content_description_product_image),
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}
