package com.commandiron.besonapp_clean_arch.presentation.signup

import com.commandiron.besonapp_clean_arch.core.StaticUrls.COMPANY_BACKGROUND_URL
import com.commandiron.besonapp_clean_arch.core.StaticUrls.CUSTOMER_BACKGROUND_URL
import com.commandiron.besonapp_clean_arch.presentation.signup.model.RegistrationFormState
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

data class SignUpState(
    val isUserSignUpBefore: Boolean = false,
    val isCustomerUiWindowOpen: Boolean = false,
    val customerWindowsBackgroundUrl: String = CUSTOMER_BACKGROUND_URL,
    val isCompanyUiWindowOpen: Boolean = false,
    val companyWindowsBackgroundUrl: String = COMPANY_BACKGROUND_URL,
    val userType: UserType = UserType.CUSTOMER,
    val registrationFormState: RegistrationFormState = RegistrationFormState(),
    val signUpFormHasError: Boolean = false,
    val enablePlaceHolder: Boolean = false,
    val loadingMessage: String = "",
    val isGoogleLoading: Boolean = false,
    val launchGoogleSignIn: Boolean = false,
    val userStateRecognizing: Boolean = true
)