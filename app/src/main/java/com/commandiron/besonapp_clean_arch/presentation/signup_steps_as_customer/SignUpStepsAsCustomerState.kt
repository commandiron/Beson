package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer

import android.net.Uri

data class SignUpStepsAsCustomerState(
    val name: String = "",
    val phoneNumber: String = "",
    val selectedPictureUri: Uri? = null,
    val profilePictureUrl: String? = null
)