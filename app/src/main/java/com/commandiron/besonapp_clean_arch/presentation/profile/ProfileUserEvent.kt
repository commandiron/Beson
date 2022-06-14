package com.commandiron.besonapp_clean_arch.presentation.profile

import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem

sealed  class ProfileUserEvent {
    object OnEditClick: ProfileUserEvent()
    object MyUpdatesDropDownIconClick: ProfileUserEvent()
    data class DeleteMyUpdate(val item: PriceItem): ProfileUserEvent()
    object FavoriteProfilesDropDownIconClick: ProfileUserEvent()
    object DeleteMyUpdateAlertDialogDismiss : ProfileUserEvent()
    object DeleteMyUpdateAlertDialogConfirm : ProfileUserEvent()
    object UnFavoriteAlertDialogDismiss : ProfileUserEvent()
    object UnFavoriteAlertDialogConfirm : ProfileUserEvent()
    data class UnFavoriteProfile(val profileUid: String): ProfileUserEvent()
    object DoneDialogDismiss : ProfileUserEvent()
}
