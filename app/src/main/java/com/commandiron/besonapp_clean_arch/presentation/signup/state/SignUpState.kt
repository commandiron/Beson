package com.commandiron.besonapp_clean_arch.presentation.signup.state

data class SignUpState(
    val isUserSignUpBefore: Boolean = false,
    val isCustomerUiWindowOpen: Boolean = false,
    val isCompanyUiWindowOpen: Boolean = false,
    val userType: UserType = UserType.CUSTOMER,
    val registrationFormState: RegistrationFormState = RegistrationFormState()
)