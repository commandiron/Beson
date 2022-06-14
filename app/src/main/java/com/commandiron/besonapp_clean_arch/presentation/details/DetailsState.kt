package com.commandiron.besonapp_clean_arch.presentation.details

import com.commandiron.besonapp_clean_arch.presentation.model.FavoriteStatus

data class DetailsState(
    val userUid: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val favoriteStatus: FavoriteStatus = FavoriteStatus.MY_PROFILE
)
