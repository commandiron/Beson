package com.commandiron.besonapp_clean_arch.presentation.details

data class DetailsState(
    val name: String = "",
    val phoneNumber: String = "",
    val imageUrl: String = "",
    val alreadyInFavorite: Boolean = false
)
