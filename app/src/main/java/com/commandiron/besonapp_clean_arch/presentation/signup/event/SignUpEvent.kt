package com.commandiron.besonapp_clean_arch.presentation.signup.event

sealed class SignUpEvent {
    object OnLogoClick: SignUpEvent()
    data class EmailChanged(val email: String): SignUpEvent()
    data class PasswordChanged(val password: String): SignUpEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): SignUpEvent()
    object OnCustomerWindowSignUpClick: SignUpEvent()
    object OnCompanyWindowSignUpClick: SignUpEvent()
    object OnLogInClick: SignUpEvent()
    object OnSignUpCustomerClick: SignUpEvent()
    object OnSignUpCompanyClick: SignUpEvent()
}