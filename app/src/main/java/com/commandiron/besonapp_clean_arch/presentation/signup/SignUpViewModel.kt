package com.commandiron.besonapp_clean_arch.presentation.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING_MESSAGE_REGISTERING
import com.commandiron.besonapp_clean_arch.core.Strings.REGISTERING
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_UP_UNSUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.model.UserStatus
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    init {
        getUserStatus()
    }

    fun onEvent(userEvent: SignUpUserEvent) {
        when(userEvent){
            is SignUpUserEvent.LogoClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = false,
                    isCompanyUiWindowOpen = false
                )
            }
            is SignUpUserEvent.CustomerWindowSignUpButtonClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = true,
                    isCompanyUiWindowOpen = false,
                    userType = UserType.CUSTOMER
                )
            }
            is SignUpUserEvent.CompanyWindowSignUpButtonClick -> {
                state = state.copy(
                    isCustomerUiWindowOpen = false,
                    isCompanyUiWindowOpen = true,
                    userType = UserType.COMPANY
                )
            }
            is SignUpUserEvent.SignInClick -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationOptions(NavigationItem.SignIn.route)))
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
            is SignUpUserEvent.SignUpUserAsCustomerClick -> {
                validateSignUpData()
                if(!state.signUpFormHasError) {
                    signUp()
                }
            }
            is SignUpUserEvent.SignUpUserAsCompanyClick -> {
                validateSignUpData()
                if(!state.signUpFormHasError) {
                    signUp()
                }
            }
            SignUpUserEvent.GoogleSignInButtonClick -> {
                state = state.copy(
                    isGoogleLoading = true,
                    launchGoogleSignIn = true
                )
            }
            is SignUpUserEvent.GoogleSignInFailed -> {
                state = state.copy(
                    isGoogleLoading = false,
                    launchGoogleSignIn = false
                )
                sendUiEvent(UiEvent.ShowSnackbar(userEvent.message))
            }
            is SignUpUserEvent.GoogleSignInSuccessful -> {
                userEvent.idToken?.let {
                    signInWithGoogleIdToken(it)
                } ?: sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
            }
        }
    }

    private fun getUserStatus(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserStatus().collect{ result ->
                when(result){
                    is Result.Loading -> {
                        state = state.copy(
                            enablePlaceHolder = true
                        )
                        sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
                    }
                    is Result.Error-> {
                        state = state.copy(
                            enablePlaceHolder = false
                        )
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        state = state.copy(
                            enablePlaceHolder = false,
                            userStatus = result.data
                        )
                        if(state.autoSignInNavigatorEnabled){
                            navigateOnUserStatus(state.userStatus)
                        }
                    }
                }
            }
        }
    }

    private fun navigateOnUserStatus(userStatus: UserStatus?){
        when(userStatus){
            UserStatus.SIGNED_IN -> {
                sendUiEvent(UiEvent.ShowSnackbar(SIGN_IN_SUCCESSFUL))
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(
                            route = NavigationItem.Profile.route,
                            popBackStack = true
                        )
                    )
                )
            }
            UserStatus.SIGNED_IN_UNFINISHED_PROFILE_CUSTOMER -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCustomer1.route)
                    )
                )
            }
            UserStatus.SIGNED_IN_UNFINISHED_PROFILE_COMPANY -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCompany1.route)
                    )
                )
            }
            UserStatus.SIGNED_IN_NO_SELECTION_CUSTOMER_OR_COMPANY -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.CustomerOrCompany.route)
                    )
                )
            }
            UserStatus.SIGNED_OUT -> {}
            null -> {}
        }
    }

    private fun validateSignUpData() {
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
                signUpFormHasError = true,
                registrationFormState = state.registrationFormState.copy(
                    emailError = emailResult.errorMessage,
                    passwordError = passwordResult.errorMessage,
                    repeatedPasswordError = repeatedPasswordResult.errorMessage
                )
            )
            return
        }

        state = state.copy(
            signUpFormHasError = false,
            registrationFormState = state.registrationFormState.copy(
                emailError = null,
                passwordError = null,
                repeatedPasswordError = null
            )
        )
    }

    private fun signUp(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases
                .signUp(state.registrationFormState.email, state.registrationFormState.password)
                .collect{ response ->
                    when(response){
                        is Result.Loading ->{
                            sendUiEvent(UiEvent.HideKeyboard)
                            sendUiEvent(UiEvent.ShowLoadingScreen(REGISTERING))
                            state = state.copy(
                                enablePlaceHolder = true,
                                loadingMessage = LOADING_MESSAGE_REGISTERING
                            )
                        }
                        is Result.Error ->{
                            state = state.copy(enablePlaceHolder = false)
                            sendUiEvent(UiEvent.HideLoadingScreen)
                            sendUiEvent(UiEvent.ShowSnackbar(SIGN_UP_UNSUCCESSFUL))
                        }
                        is Result.Success ->{
                            state = state.copy(
                                autoSignInNavigatorEnabled = false,
                                enablePlaceHolder = false
                            )
                            sendUiEvent(UiEvent.HideLoadingScreen)
                            sendUiEvent(UiEvent.ShowSnackbar(SIGN_UP_SUCCESSFUL))
                            navigateOnUserType(state.userType)
                        }
                    }
                }
        }
    }

    private fun navigateOnUserType(userType: UserType){
        when(userType){
            UserType.CUSTOMER ->
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCustomer1.route)
                    )
                )
            UserType.COMPANY ->
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCompany1.route)
                    )
                )
        }
    }

    private fun signInWithGoogleIdToken(idToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.signInWithGoogleIdToken(idToken).collect{
                when(it){
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(Strings.NOT_LOGGED_WITH_GOOGLE_ACCOUNT))
                    }
                    is Result.Loading -> {
                        state = state.copy(
                            isGoogleLoading = false,
                            launchGoogleSignIn = false
                        )
                    }
                    is Result.Success -> {
                        state = state.copy(
                            isGoogleLoading = false,
                            launchGoogleSignIn = false
                        )
                    }
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(uiEvent)
        }
    }
}