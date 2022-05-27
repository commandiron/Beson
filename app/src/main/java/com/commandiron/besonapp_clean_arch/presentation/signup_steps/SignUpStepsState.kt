package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import android.net.Uri

data class SignUpStepsState(
    val name: String = "",
    val phoneNumber: String = "",
    val profilePictureUri: Uri? = null
)