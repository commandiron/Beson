package com.example.besonapp.presentation.floating_components

import android.app.UiModeManager
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.ui.theme.logoColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreenLogoAnimation(
    modifier: Modifier = Modifier,
    onFinish:() -> Unit,
){
    val scope = rememberCoroutineScope()

    val logoRotateAnim = remember { Animatable(0f) }
    val logoBottomPaddingAnim = remember { Animatable(10f) }
    val textAlphaAnim = remember { Animatable(0f) }
    val textPaddingAnim = remember { Animatable(200f) }
    val textRotationXAnim = remember { Animatable(-30f) }

    LaunchedEffect(key1 = Unit){
        scope.launch {
            logoRotateAnim.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    delayMillis = 1500,
                    durationMillis = 1000,
                )
            )
            logoBottomPaddingAnim.animateTo(
                targetValue = 40f,
                animationSpec = tween(
                    delayMillis = 0,
                    durationMillis = 1000,
                )
            )
        }
        scope.launch {
            textAlphaAnim.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    delayMillis = 2800,
                    durationMillis = 500
                )
            )
        }
        scope.launch {
            textPaddingAnim.animateTo(
                targetValue = 60f,
                animationSpec = tween(
                    delayMillis = 2800,
                    durationMillis = 500,
                )
            )
        }
        scope.launch {
            textRotationXAnim.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    delayMillis = 2800,
                    durationMillis = 500,
                )
            ).also {
                delay(2000)
                onFinish()
            }
        }
    }
    Icon(
        imageVector = Icons.Default.DocumentScanner,
        contentDescription = null,
        modifier = modifier
            .padding(
                bottom = Dp(logoBottomPaddingAnim.value)
            )
            .size(70.dp)
            .alpha(1f)
            .rotate(logoRotateAnim.value)
        ,
        tint = logoColor
    )
    Text(
        text = Strings.BESON,
        modifier = Modifier
            .alpha(alpha = textAlphaAnim.value)
            .padding(top = Dp(textPaddingAnim.value))
            .graphicsLayer { rotationX = textRotationXAnim.value },
        color = logoColor,
        style = MaterialTheme.typography.h1
    )
}