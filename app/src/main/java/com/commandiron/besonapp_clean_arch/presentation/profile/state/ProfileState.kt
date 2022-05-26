package com.commandiron.besonapp_clean_arch.presentation.profile.state

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class ProfileState(
    val name: String = "İsim Soyad",
    val imageUrl: String? = null,
    val profileHeaderHeight: Dp = 0.dp
)