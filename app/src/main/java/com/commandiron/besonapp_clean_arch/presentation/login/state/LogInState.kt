package com.commandiron.besonapp_clean_arch.presentation.login.state

data class LogInState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
)