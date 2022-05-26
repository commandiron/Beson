package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DocumentScanner
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor

@Composable
fun AppLogoIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector = Icons.Default.DocumentScanner,
    tint: Color = PrimaryColor
){
    Icon(
        imageVector = imageVector,
        contentDescription = null,
        modifier = modifier,
        tint = tint
    )
}