package com.commandiron.besonapp_clean_arch.presentation.signin

sealed class SignInUserEvent {
    data class EmailChanged(val email: String): SignInUserEvent()
    data class PasswordChanged(val password: String): SignInUserEvent()
    object OnSignInClick: SignInUserEvent()
    object GoogleSignInButtonClick: SignInUserEvent()
    data class GoogleSignInSuccessful(val idToken: String?): SignInUserEvent()
    data class GoogleSignInFailed(val message: String): SignInUserEvent()
    object OnSignUpClick: SignInUserEvent()
}