package com.commandiron.besonapp_clean_arch.presentation.signin

data class SignInState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isGoogleLoading: Boolean = false,
    val launchGoogleSignIn: Boolean = false,
)