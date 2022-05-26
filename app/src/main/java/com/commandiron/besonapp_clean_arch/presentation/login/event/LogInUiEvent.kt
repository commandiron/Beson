package com.commandiron.besonapp_clean_arch.presentation.login.event

sealed class LogInUiEvent {
    object LogInValidationAndFirebaseLogInSuccess: LogInUiEvent()
    object NavigateToSignUp: LogInUiEvent()
}