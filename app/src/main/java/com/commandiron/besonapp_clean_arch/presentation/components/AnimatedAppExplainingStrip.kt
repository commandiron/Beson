package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.core.Strings.APP_STATEMENT
import com.commandiron.besonapp_clean_arch.presentation.components.toDpSize
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.TertiaryColor

@Composable
fun AnimatedAppExplainingStrip(
    screenSize: Size,
    modifier: Modifier = Modifier,
    isAnimated: Boolean = false
){
    val spacing = LocalSpacing.current
    val dpScreenSize = screenSize.toDpSize()

    val offsetAnimInitialValue = if(isAnimated) -dpScreenSize.width.value else 0f
    val offsetAnim = remember { Animatable(offsetAnimInitialValue) }
    LaunchedEffect(key1 = Unit){
        if(isAnimated){
            offsetAnim.animateTo(
                targetValue = 0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(
                        delayMillis = 2000,
                        durationMillis = 1500
                    )
                )
            )
        }
    }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .offset(x = Dp(offsetAnim.value)),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(spacing.spaceMediumLarge),
                imageVector = Icons.Default.DocumentScanner,
                contentDescription = null,
                tint = TertiaryColor
            )
            Spacer(modifier = Modifier.padding(spacing.spaceExtraSmall))
            Text(
                text = APP_STATEMENT,
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}