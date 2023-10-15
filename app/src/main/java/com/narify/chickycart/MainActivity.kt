package com.narify.chickycart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.narify.chickycart.ui.theme.ChickyCartTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Set up splash screen and make it last for longer time
        var keepSplashOnScreen = true
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        lifecycleScope.launch(Dispatchers.IO) {
            delay(1000)
            keepSplashOnScreen = false
        }

        super.onCreate(savedInstanceState)
        setContent {
            ChickyCartTheme {
                ChickyCartNavGraph()
            }
        }
    }
}



