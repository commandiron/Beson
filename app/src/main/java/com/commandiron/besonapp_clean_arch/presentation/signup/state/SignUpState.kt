package com.commandiron.besonapp_clean_arch.presentation.signup.state

data class SignUpState(
    val isUserSignUpBefore: Boolean = false,
    val isCustomerUiWindowOpen: Boolean = false,
    val customerWindowsBackgroundUrl: String =
        "https://archello.s3.eu-central-1.amazonaws.com/images/2021/02/28/addline-group-interior-design-of-the-construction-company-office-offices-archello.1614525239.7456.jpg",
    val isCompanyUiWindowOpen: Boolean = false,
    val companyWindowsBackgroundUrl: String =
        "https://assets.bizclikmedia.net/668/1c6647d96a362cc2ce8db0361b509e77:0b0fab1f532a05d304b7611bb9a475f5/mace1-jpeg.webp",
    val userType: UserType = UserType.CUSTOMER,
    val registrationFormState: RegistrationFormState = RegistrationFormState()
)