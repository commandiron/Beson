package com.commandiron.besonapp_clean_arch.presentation.profile.model

data class Profile(
    val profileId: Int = 0,
    val name: String = "",
    val phoneNumber: String = "",
    val imageUrl: String? = null,
)
