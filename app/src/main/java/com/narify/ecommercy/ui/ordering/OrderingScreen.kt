package com.narify.ecommercy.ui.ordering

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OrderingRoute() {
    OrderingScreen()
}

@Composable
fun OrderingScreen() {

}

@Preview(device = "id:pixel_2", showSystemUi = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OrderingScreenPreview() {
    OrderingScreen()
}
