package com.commandiron.besonapp_clean_arch.presentation.signup.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun AnimatableSignUpWindow(
    modifier: Modifier = Modifier,
    title: String,
    details: String,
    buttonText: String,
    backgroundImageUrl: String,
    surfaceColor: Color,
    targetOffsetValue: Float,
    isUiWindowOpen: Boolean,
    onButtonClick: () -> Unit,
) {
    val spacing = LocalSpacing.current
    val offsetAnim = remember { Animatable(0f) }
    LaunchedEffect(key1 = isUiWindowOpen){
        if(!isUiWindowOpen){
            offsetAnim.animateTo(
                targetValue = 0f,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
        }else{
            offsetAnim.animateTo(
                targetValue = targetOffsetValue,
                animationSpec = tween(
                    durationMillis = 1000
                )
            )
        }
    }
    Surface(
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.onSurface)
            .offset(y = Dp(offsetAnim.value)),
        color = surfaceColor
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {
            val matrix = ColorMatrix()
            matrix.setToSaturation(0F)
            Image(
                modifier = modifier.fillMaxSize(),
                painter = rememberAsyncImagePainter(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(backgroundImageUrl)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                colorFilter = ColorFilter.colorMatrix(matrix),
                alpha = 0.1f
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(spacing.spaceSmall))
                Text(
                    text = details,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.45f)
                        .clickable { onButtonClick() },
                    shape = RoundedCornerShape(spacing.spaceMedium),
                    color = Color.White,
                    shadowElevation = spacing.defaultElevation
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            modifier = Modifier.padding(spacing.spaceExtraSmall),
                            text = buttonText,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
    }
}