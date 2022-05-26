package com.commandiron.besonapp_clean_arch.presentation.signup.event

sealed class SignUpUserEvent {
    object OnLogoClick: SignUpUserEvent()
    data class EmailChanged(val email: String): SignUpUserEvent()
    data class PasswordChanged(val password: String): SignUpUserEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String): SignUpUserEvent()
    object OnCustomerWindowSignUpUserClick: SignUpUserEvent()
    object OnCompanyWindowSignUpUserClick: SignUpUserEvent()
    object OnLogInClick: SignUpUserEvent()
    object OnSignUpUserAsCustomerClick: SignUpUserEvent()
    object OnSignUpUserAsCompanyClick: SignUpUserEvent()
}