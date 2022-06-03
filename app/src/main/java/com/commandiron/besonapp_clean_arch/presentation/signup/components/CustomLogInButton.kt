package com.commandiron.besonapp_clean_arch.presentation.signup.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.modifier.modifierLocalConsumer
import com.commandiron.besonapp_clean_arch.ui.theme.LocalSpacing
import com.commandiron.besonapp_clean_arch.ui.theme.TertiaryColor

@Composable
fun CustomLogInButton(
    modifier: Modifier = Modifier,
    placeholderModifier: Modifier = Modifier,
    text: String,
    onClick:() -> Unit
){
    val spacing = LocalSpacing.current
    Surface(
        modifier = modifier.then(placeholderModifier),
        shape = CircleShape,
        color = TertiaryColor,
        shadowElevation = spacing.defaultElevation
    ) {
        Box(
            modifier = Modifier.padding(spacing.spaceMedium),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .clickable{ onClick() },
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}