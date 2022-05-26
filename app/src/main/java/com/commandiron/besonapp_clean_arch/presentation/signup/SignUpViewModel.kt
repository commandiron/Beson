package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpUiEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpUserEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.state.SignUpState
import com.commandiron.besonapp_clean_arch.presentation.signup.state.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(SignUpState())
        private set

    private val _uiEvent = Channel<SignUpUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignUpUserEvent) {
        when(userEvent){
            is SignUpUserEvent.OnLogoClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = false,
                    isCompanyUiWindowOpen = false
                )
            }
            is SignUpUserEvent.OnCustomerWindowSignUpUserClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = false,
                    userType = UserType.CUSTOMER
                )
            }
            is SignUpUserEvent.OnCompanyWindowSignUpUserClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = false,
                    isCompanyUiWindowOpen = true,
                    userType = UserType.COMPANY
                )
            }
            is SignUpUserEvent.OnLogInClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = true
                )
            }
            is SignUpUserEvent.EmailChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        email = userEvent.email,
                    )
                )
            }
            is SignUpUserEvent.PasswordChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        password = userEvent.password
                    )
                )
            }
            is SignUpUserEvent.RepeatedPasswordChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        repeatedPassword = userEvent.repeatedPassword
                    )
                )
            }
            is SignUpUserEvent.OnSignUpUserAsCustomerClick -> {
                submitSignUpData()
            }
            is SignUpUserEvent.OnSignUpUserAsCompanyClick -> {
                submitSignUpData()
            }
        }
    }

    private fun submitSignUpData() {
        val emailResult = useCases.validateEmail.execute(state.registrationFormState.email)
        val passwordResult = useCases.validatePassword.execute(state.registrationFormState.password)
        val repeatedPasswordResult = useCases
            .validateRepeatedPassword
            .execute(
                password = state.registrationFormState.password,
                repeatedPassword = state.registrationFormState.repeatedPassword
            )

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult
        ).any{ !it.successful }

        if(hasError){
            state = state.copy(
                registrationFormState = state.registrationFormState.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            )
            return
        }
        //Eğer firebaseden başarılı olursa
        when(state.userType){
            UserType.CUSTOMER -> sendUiEvent(SignUpUiEvent.SignUpValidationAndFirebaseRegisterSuccessAsCustomer)
            UserType.COMPANY -> sendUiEvent(SignUpUiEvent.SignUpValidationAndFirebaseRegisterSuccessAsCompany)
        }
        sendUiEvent(SignUpUiEvent.ShowSnackbar(SIGN_UP_SUCCESSFUL))
    }

    private fun sendUiEvent(uiEvent: SignUpUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}