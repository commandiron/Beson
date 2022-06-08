package com.commandiron.besonapp_clean_arch.presentation.signup

sealed class SignUpUserEvent {
    object LogoClick: SignUpUserEvent()
    data class EmailChanged(val email: String): SignUpUserEvent()
    data class PasswordChanged(val password: String): SignUpUserEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): SignUpUserEvent()
    object CustomerWindowSignUpButtonClick: SignUpUserEvent()
    object CompanyWindowSignUpButtonClick: SignUpUserEvent()
    object SignInClick: SignUpUserEvent()
    object SignUpUserAsCustomerClick: SignUpUserEvent()
    object SignUpUserAsCompanyClick: SignUpUserEvent()
    object GoogleSignInButtonClick: SignUpUserEvent()
    data class GoogleSignInSuccessful(val idToken: String?): SignUpUserEvent()
    data class GoogleSignInFailed(val message: String): SignUpUserEvent()
}