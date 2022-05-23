package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor

@Composable
fun AppLogoIcon(
    modifier: Modifier = Modifier,
    tint: Color = PrimaryColor,
    paddingBottomAnimValue: Float = 0f,
    sizeAnimValue: Dp = 100.dp,
    alphaAnimValue:Float = 1f,
    rotateAnimValue:Float = 0f
){
    Icon(
        modifier = modifier
            .padding(bottom = Dp(paddingBottomAnimValue))
            .size(sizeAnimValue)
            .alpha(alphaAnimValue)
            .rotate(rotateAnimValue),
        imageVector = Icons.Default.DocumentScanner,
        contentDescription = null,
        tint = tint
    )
}