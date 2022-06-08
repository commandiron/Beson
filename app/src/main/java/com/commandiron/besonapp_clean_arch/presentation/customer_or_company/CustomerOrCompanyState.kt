package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

import com.commandiron.besonapp_clean_arch.core.StaticUrls.COMPANY_BACKGROUND_URL
import com.commandiron.besonapp_clean_arch.core.StaticUrls.CUSTOMER_BACKGROUND_URL
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class CustomerOrCompanyState(
    val customerWindowsBackgroundUrl: String = CUSTOMER_BACKGROUND_URL,
    val companyWindowsBackgroundUrl: String = COMPANY_BACKGROUND_URL,
    val showAlertDialog: Boolean = false,
    val alertDialogTitle: String = "",
    val userType: UserType? = null
)
