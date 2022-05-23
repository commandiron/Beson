package com.example.besonapp.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.presentation.components.AppLogoIcon
import com.commandiron.besonapp_clean_arch.ui.theme.SplashBackgroundColor


@Composable
fun SignUpScreenLogoAnimation(
    modifier: Modifier = Modifier,
    onSignUpScreenLogoClick:() -> Unit
){
    val sizeAnim = remember { Animatable(100f) }
    LaunchedEffect(key1 = Unit){
        sizeAnim.animateTo(
            targetValue = 50f,
            animationSpec = tween(
                durationMillis = 2000
            )
        )
    }
    val rotateAnim= remember { Animatable(0f) }
    LaunchedEffect(rotateAnim) {
        for(i in 0 until 10000){
            rotateAnim.animateTo(
                targetValue = 360f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(800),
                    initialStartOffset = StartOffset(5000)
                )
            )
            rotateAnim.animateTo(
                targetValue = 0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(800)
                )
            )
            rotateAnim.animateTo(
                targetValue = -360f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(800),
                    initialStartOffset = StartOffset(5000)
                )
            )
            rotateAnim.animateTo(
                targetValue = 0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(800)
                )
            )
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .clip(CircleShape)
                .clickable{ onSignUpScreenLogoClick() },
            color = SplashBackgroundColor
        ) {
            AppLogoIcon(
                modifier = Modifier.padding(20.dp),
                sizeAnimValue = Dp(sizeAnim.value),
                rotateAnimValue = rotateAnim.value
            )
        }
    }
}