package com.commandiron.besonapp_clean_arch.presentation.hot_splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.TertiaryColor
import com.example.besonapp.presentation.floating_components.SplashScreenLogoAnimation

@Composable
fun HotSplashScreen() {
    val navController = LocalNavController.current
    SplashScreenLogoAnimation(
        modifier = Modifier
            .fillMaxSize()
            .background(TertiaryColor),
        onFinish = {
            navController.navigate(
                NavigationItem.Intro.route
            )
        }
    )
}