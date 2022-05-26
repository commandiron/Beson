package com.commandiron.besonapp_clean_arch.presentation.profile.event

sealed class ProfileUiEvent {
    object NavigateToEditProfile: ProfileUiEvent()
    object NavigateToMyPriceUpdates: ProfileUiEvent()
}
