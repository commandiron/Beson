package com.commandiron.besonapp_clean_arch.ui.theme

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.systemuicontroller.SystemUiController

val LocalSpacing = compositionLocalOf { Dimensions() }
data class Dimensions(
    val default: Dp = 0.dp,
    val spaceExtraSmall: Dp = 4.dp,
    val spaceSmall: Dp = 8.dp,
    val spaceMedium: Dp = 16.dp,
    val spaceMediumLarge: Dp = 22.dp,
    val spaceLarge: Dp = 32.dp,
    val spaceExtraLarge: Dp = 64.dp,
    val spaceXXLarge: Dp = 128.dp,
    val spaceXXXLarge: Dp = 256.dp,
    val dropDownMenuHeight: Dp = 110.dp,
    val topBarHeight: Dp = 36.dp,
    val navigationHeight: Dp = 56.dp,
    val expandableMenuHeight: Dp = 200.dp,
    val defaultElevation: Dp = 6.dp,
    val defaultButtonWidth: Dp = 100.dp,
    val defaultFilterBoxHeight: Dp = 40.dp,
    val defaultSearchTextFieldHeight: Dp = 50.dp
)
val LocalPermissionsState = compositionLocalOf<MultiplePermissionsState> {
    error("No Permission State")
}
val LocalNavController = compositionLocalOf<NavHostController> {
    error("No Nav Controller")
}
val LocalSystemUiController = compositionLocalOf<SystemUiController> {
    error("No System Ui Controller")
}
val LocalFabState = compositionLocalOf<MutableTransitionState<Boolean>> {
    error("No Fab State")
}
fun getProvidedValues(
    permissionsState: MultiplePermissionsState,
    navController: NavHostController,
    systemUiController: SystemUiController,
    fabState: MutableTransitionState<Boolean>
): Array<ProvidedValue<*>> {
    return arrayOf(
        LocalRippleTheme provides NoRippleTheme,
        LocalSpacing provides Dimensions(),
        LocalPermissionsState provides permissionsState,
        LocalNavController provides navController,
        LocalSystemUiController provides systemUiController,
        LocalFabState provides fabState
    )
}


