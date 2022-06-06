package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING_MESSAGE_REGISTERING
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_UNSUCCESSFUL
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.UserState
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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

    init {
        viewModelScope.launch {
            useCases.getUserState().collect{ result ->
                when(result){
                    is Result.Loading -> {
                    }
                    is Result.Error-> {
                    }
                    is Result.Success -> {
                        val userState = result.data
                        when(userState){
                            UserState.SIGNED_IN -> {
                                sendUiEvent(UiEvent.ShowSnackbar(SIGN_IN_SUCCESSFUL))
                                sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
                            }
                            UserState.SIGNED_IN_UNFINISHED_PROFILE_CUSTOMER -> {
                                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCustomer1.route))
                            }
                            UserState.SIGNED_IN_UNFINISHED_PROFILE_COMPANY -> {
                                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany1.route))
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

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
            is SignUpUserEvent.OnSignInClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = true
                )
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignIn.route))
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

        state = state.copy(
            registrationFormState = state.registrationFormState.copy(
                emailError = null,
                passwordError = null,
                repeatedPasswordError = null
            )
        )

        signUp()
    }

    private fun signUp(){
        viewModelScope.launch {
            useCases
                .signUp(state.registrationFormState.email, state.registrationFormState.password)
                .collect{ response ->
                    when(response){
                        is Result.Loading ->{
                            sendUiEvent(UiEvent.HideKeyboard)
                            state = state.copy(
                                isLoading = true,
                                loadingMessage = LOADING_MESSAGE_REGISTERING
                            )
                        }
                        is Result.Error ->{
                            state = state.copy(isLoading = false)
                            sendUiEvent(UiEvent.ShowSnackbar(SIGN_UP_UNSUCCESSFUL))
                        }
                        is Result.Success ->{
                            delay(3000) //Fake delay for user.
                            state = state.copy(isLoading = false)
                            sendUiEvent(UiEvent.ShowSnackbar(SIGN_UP_SUCCESSFUL))
                            when(state.userType){
                                UserType.CUSTOMER ->
                                    sendUiEvent(
                                        UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCustomer1.route)
                                    )
                                UserType.COMPANY ->
                                    sendUiEvent(
                                        UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany1.route)
                                    )
                            }
                        }
                    }
                }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}