package com.commandiron.besonapp_clean_arch.presentation.intro

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.besonapp.presentation.floating_components.SplashScreenLogoAnimation

@Composable
fun IntroScreen(
    onNextClick:() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize().border(1.dp, Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "INTRO SCREEN")
        SplashScreenLogoAnimation()
    }
}