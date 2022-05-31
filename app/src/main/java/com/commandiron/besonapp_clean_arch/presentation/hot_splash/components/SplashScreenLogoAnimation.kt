package com.example.besonapp.presentation.floating_components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings.APP_STATEMENT
import com.commandiron.besonapp_clean_arch.core.Strings.BESON
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreenLogoAnimation(
    modifier: Modifier = Modifier,
    onFinish:() -> Unit,
){
    val spacing = LocalSpacing.current
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
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.DocumentScanner,
            contentDescription = null,
            modifier = Modifier
                .padding(
                    bottom = Dp(logoBottomPaddingAnim.value)
                )
                .size(70.dp)
                .alpha(1f)
                .rotate(logoRotateAnim.value)
            ,
            tint = PrimaryColor
        )
        Text(
            text = BESON,
            modifier = Modifier
                .alpha(alpha = textAlphaAnim.value)
                .padding(top = Dp(textPaddingAnim.value))
                .graphicsLayer { rotationX = textRotationXAnim.value },
            color = PrimaryColor,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = APP_STATEMENT,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = spacing.spaceExtraLarge)
                .alpha(alpha = textAlphaAnim.value),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium,
            textAlign = TextAlign.Center
        )
    }
}