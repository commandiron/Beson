package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.ui.unit.Dp

sealed  class ProfileUserEvent {
    data class OnProfileHeaderVerticalDrag(val profileHeaderHeight: Dp): ProfileUserEvent()
    object OnEditClick: ProfileUserEvent()
    object MyUpdatesDropDownIconClick: ProfileUserEvent()
    data class DeleteMyUpdate(val itemId: Int): ProfileUserEvent()
    object FavoriteProfilesDropDownIconClick: ProfileUserEvent()
    object DeleteMyUpdateAlertDialogDismiss : ProfileUserEvent()
    object DeleteMyUpdateAlertDialogConfirm : ProfileUserEvent()
    object UnFavoriteAlertDialogDismiss : ProfileUserEvent()
    object UnFavoriteAlertDialogConfirm : ProfileUserEvent()
    data class UnFavoriteProfile(val profileId: Int): ProfileUserEvent()
    object DoneDialogDismiss : ProfileUserEvent()
}
