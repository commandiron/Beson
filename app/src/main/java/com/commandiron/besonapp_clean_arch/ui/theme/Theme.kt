package com.commandiron.besonapp_clean_arch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = PrimaryColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor,
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    background = BackgroundColor,
    onBackground = OnBackgroundColor,
    onSurface = OnSurfaceColor,
)

@Composable
fun BesonApp_Clean_ArchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    CompositionLocalProvider(LocalSpacing provides Dimensions()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}