package com.commandiron.besonapp_clean_arch.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.SystemUiController
import kotlinx.coroutines.CoroutineScope

//Local Provide test için sıkıntı çıkarıyor, fakat dışarı çıkarırsam complexity artıyor.

val LocalSpacing = compositionLocalOf { Dimensions() }

data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val spaceXXLarge: Dp = 128.dp,
    val topBarHeight: Dp = 36.dp,
    val navigationHeight: Dp = 56.dp,
    val defaultElevation: Dp = 6.dp,
    val defaultButtonWidth: Dp = 100.dp
)

val LocalNavController = compositionLocalOf<NavHostController> {
    error("No Nav Controller")
}
val LocalCoroutineScope = compositionLocalOf<CoroutineScope> {
    error("No CoroutineScope")
}
val LocalSnackbarHostState = compositionLocalOf<SnackbarHostState> {
    error("No SnackbarHostState provided")
}
val LocalSystemUiController = compositionLocalOf<SystemUiController> {
    error("No System Ui Controller")
}

fun getProvidedValues(
    navController: NavHostController,
    coroutineScope: CoroutineScope,
    scaffoldState: ScaffoldState,
    systemUiController: SystemUiController
): Array<ProvidedValue<*>> {
    return arrayOf(
        LocalRippleTheme provides NoRippleTheme,
        LocalSpacing provides Dimensions(),
        LocalNavController provides navController,
        LocalCoroutineScope provides coroutineScope,
        LocalSnackbarHostState provides scaffoldState.snackbarHostState,
        LocalSystemUiController provides systemUiController
    )
}

object NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = Color.Unspecified

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleAlpha(
            draggedAlpha = 0.0f,
            focusedAlpha = 0.0f,
            hoveredAlpha = 0.0f,
            pressedAlpha = 0.0f
        )
}

private object DefaultRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = MaterialTheme.colors.background

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        Color.Black,
        lightTheme = !isSystemInDarkTheme()
    )
}

