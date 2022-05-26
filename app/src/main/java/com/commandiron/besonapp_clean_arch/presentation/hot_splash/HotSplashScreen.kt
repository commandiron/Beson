package com.commandiron.besonapp_clean_arch.presentation.hot_splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.ui.theme.LocalNavController
import com.commandiron.besonapp_clean_arch.ui.theme.SplashBackgroundColor
import com.example.besonapp.presentation.floating_components.SplashScreenLogoAnimation

@Composable
fun HotSplashScreen() {
    val navController = LocalNavController.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SplashBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        SplashScreenLogoAnimation(onFinish = {
            navController.navigate(
                NavigationItem.Intro.route
            )
        })
    }
}