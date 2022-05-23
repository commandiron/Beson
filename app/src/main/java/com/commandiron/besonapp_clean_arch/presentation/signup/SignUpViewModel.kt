package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.presentation.signup.event.SignUpEvent
import com.commandiron.besonapp_clean_arch.presentation.signup.state.SignUpState
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

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SignUpEvent) {
        when(event){
            is SignUpEvent.OnLogoClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = false,
                    isCompanyUiWindowOpen = false
                )
            }
            is SignUpEvent.OnCustomerWindowSignUpClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = false
                )
            }
            is SignUpEvent.OnCompanyWindowSignUpClick -> {
                state = state.copy(
                    isCompanyUiWindowOpen = true,
                    isCustomerUiWindowOpen = false
                )
            }
            is SignUpEvent.OnLogInClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = true
                )
            }
            is SignUpEvent.EmailChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        email = event.email,
                    )
                )
            }
            is SignUpEvent.PasswordChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        password = event.password
                    )
                )
            }
            is SignUpEvent.RepeatedPasswordChanged -> {
                state = state.copy(
                    registrationFormState = state.registrationFormState.copy(
                        repeatedPassword = event.repeatedPassword
                    )
                )
            }
            is SignUpEvent.OnSignUpCustomerClick -> {
                state = state.copy(
                    userType = UserType.CUSTOMER
                )
                submitSignUpData()
            }
            is SignUpEvent.OnSignUpCompanyClick -> {
                state = state.copy(
                    userType = UserType.COMPANY
                )
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
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Success)
            //Firebase Kayıt işlemi yap.
        }
    }


}