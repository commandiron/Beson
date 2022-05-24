package com.commandiron.besonapp_clean_arch.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.DpSize

@Composable
fun Size.toDpSize(): DpSize{
    val width = with(LocalDensity.current) {
        this@toDpSize.width.toDp()
    }
    val height = with(LocalDensity.current) {
        this@toDpSize.height.toDp()
    }
    return DpSize(width, height)
}