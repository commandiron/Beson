package com.commandiron.besonapp_clean_arch.presentation.signin

import com.commandiron.besonapp_clean_arch.presentation.model.UserStatus

data class SignInState(
    val userStatus: UserStatus? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val isGoogleLoading: Boolean = false,
    val launchGoogleSignIn: Boolean = false,
)