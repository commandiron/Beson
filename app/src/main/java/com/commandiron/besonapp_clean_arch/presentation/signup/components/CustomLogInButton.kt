package com.commandiron.besonapp_clean_arch.presentation.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.SplashBackgroundColor

@Composable
fun CustomLogInButton(
    modifier: Modifier,
    text: String,
    onClick:() -> Unit
){
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = SplashBackgroundColor,
        elevation = spacing.defaultElevation,
        contentColor = MaterialTheme.colors.primary
    ) {
        Box(
            modifier = Modifier.padding(spacing.spaceMedium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .clickable{ onClick() },
                text = text,
                style = MaterialTheme.typography.h5.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}