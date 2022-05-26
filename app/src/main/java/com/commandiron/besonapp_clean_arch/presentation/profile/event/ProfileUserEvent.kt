package com.commandiron.besonapp_clean_arch.presentation.profile.event

import androidx.compose.ui.unit.Dp

sealed  class ProfileUserEvent {
    data class OnProfileHeaderVerticalDrag(val profileHeaderHeight: Dp): ProfileUserEvent()
    object OnEditClick: ProfileUserEvent()
    object OnMyUpdatesClick: ProfileUserEvent()
}
