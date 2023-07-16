package com.narify.ecommercy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.narify.ecommercy.EcommercyDestinations.PRODUCT_DETAILS_ROUTE
import com.narify.ecommercy.ui.theme.EcommercyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EcommercyTheme {
                EcommercyNavGraph(startDestination = PRODUCT_DETAILS_ROUTE)
            }
        }
    }
}



