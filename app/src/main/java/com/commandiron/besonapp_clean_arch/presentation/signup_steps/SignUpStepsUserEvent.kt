package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import android.net.Uri

sealed class SignUpStepsUserEvent {
    object OnBackToSignUpClick: SignUpStepsUserEvent()
    data class NameChanged(val name: String): SignUpStepsUserEvent()
    data class PhoneNumberChanged(val phoneNumber: String): SignUpStepsUserEvent()
    data class PictureChanged(val uri: Uri?): SignUpStepsUserEvent()
    object OnNextClick1: SignUpStepsUserEvent()
    object OnNextClick2: SignUpStepsUserEvent()
    object OnCompleteClick: SignUpStepsUserEvent()
}