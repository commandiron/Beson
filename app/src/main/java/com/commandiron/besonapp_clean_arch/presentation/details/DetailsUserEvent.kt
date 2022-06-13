package com.commandiron.besonapp_clean_arch.presentation.details

sealed class DetailsUserEvent {
    object Favorite: DetailsUserEvent()
    object Back: DetailsUserEvent()
}
