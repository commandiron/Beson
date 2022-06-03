package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer

import android.net.Uri

sealed class SignUpStepsAsCustomerUserEvent {
    object OnBackToSignUpClick: SignUpStepsAsCustomerUserEvent()
    data class NameChanged(val name: String): SignUpStepsAsCustomerUserEvent()
    data class PhoneNumberChanged(val phoneNumber: String): SignUpStepsAsCustomerUserEvent()
    data class PictureChanged(val uri: Uri?): SignUpStepsAsCustomerUserEvent()
    object NameScreenNext: SignUpStepsAsCustomerUserEvent()
    object PhoneNumberScreenNext: SignUpStepsAsCustomerUserEvent()
    object PictureScreenNext: SignUpStepsAsCustomerUserEvent()
}