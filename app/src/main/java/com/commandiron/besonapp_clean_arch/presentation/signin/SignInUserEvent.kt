package com.commandiron.besonapp_clean_arch.presentation.signin

sealed class SignInUserEvent {
    data class EmailChanged(val email: String): SignInUserEvent()
    data class PasswordChanged(val password: String): SignInUserEvent()
    object OnSignInClick: SignInUserEvent()
    object OnSignUpClick: SignInUserEvent()
}