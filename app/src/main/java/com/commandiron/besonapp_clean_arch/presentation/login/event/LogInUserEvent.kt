package com.commandiron.besonapp_clean_arch.presentation.login.event

sealed class LogInUserEvent {
    data class EmailChanged(val email: String): LogInUserEvent()
    data class PasswordChanged(val password: String): LogInUserEvent()
    object OnLogInClick: LogInUserEvent()
    object OnSignUpClick: LogInUserEvent()
}