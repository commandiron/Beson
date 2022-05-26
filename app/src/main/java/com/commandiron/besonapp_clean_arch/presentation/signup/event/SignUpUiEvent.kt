package com.commandiron.besonapp_clean_arch.presentation.signup.event

sealed class SignUpUiEvent {
    object SignUpValidationAndFirebaseRegisterSuccessAsCustomer: SignUpUiEvent()
    object SignUpValidationAndFirebaseRegisterSuccessAsCompany: SignUpUiEvent()
    data class ShowSnackbar(val message: String): SignUpUiEvent()
}