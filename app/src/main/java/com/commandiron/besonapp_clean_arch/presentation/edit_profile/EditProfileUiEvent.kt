package com.commandiron.besonapp_clean_arch.presentation.edit_profile

sealed class EditProfileUiEvent {
    object ProfileUpdateCanceled: EditProfileUiEvent()
    object ProfileUpdateSuccess: EditProfileUiEvent()
}
