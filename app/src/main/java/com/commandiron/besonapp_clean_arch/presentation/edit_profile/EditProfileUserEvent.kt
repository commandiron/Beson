package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import android.net.Uri

sealed class EditProfileUserEvent {
    object Cancel: EditProfileUserEvent()
    data class NameChanged(val name: String): EditProfileUserEvent()
    data class PhoneNumberChanged(val phoneNumber: String): EditProfileUserEvent()
    data class PictureChanged(val uri: Uri?): EditProfileUserEvent()
    object Save: EditProfileUserEvent()
    object LogOut: EditProfileUserEvent()
}
