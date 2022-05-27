package com.commandiron.besonapp_clean_arch.presentation.signup_steps

sealed class SignUpStepsUiEvent {
    object NavigateToSignUpScreen: SignUpStepsUiEvent()
    object NavigateToSignUpStepsScreen2: SignUpStepsUiEvent()
    object RegistrationSuccess: SignUpStepsUiEvent()
}