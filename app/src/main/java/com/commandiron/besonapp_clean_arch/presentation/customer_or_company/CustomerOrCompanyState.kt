package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class CustomerOrCompanyState(
    val showAlertDialog: Boolean = false,
    val alertDialogTitle: String = "",
    val userType: UserType? = null
)
