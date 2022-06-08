package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.R
import com.commandiron.besonapp_clean_arch.core.Strings.SIGNING_IN
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing

@Composable
fun GoogleSignInButton(
    modifier: Modifier = Modifier,
    text: String,
    loadingText: String = SIGNING_IN,
    icon: Painter = painterResource(id = R.drawable.ic_google_logo),
    isLoading: Boolean = false,
    shape: Shape = Shapes.Full,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    borderColor: Color = Color.LightGray,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit
) {
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier.clickable(
            enabled = !isLoading,
            onClick = onClick
        ),
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(spacing.spaceSmall))
            Text(
                text = if (isLoading) loadingText else text,
                style = MaterialTheme.typography.labelSmall,
                color = textColor
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(spacing.spaceMedium))
                CircularProgressIndicator(
                    modifier = Modifier
                        .height(16.dp)
                        .width(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}