package com.commandiron.besonapp_clean_arch.presentation.signin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING
import com.commandiron.besonapp_clean_arch.core.Strings.NOT_LOGGED_WITH_GOOGLE_ACCOUNT
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_IN_UNSUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.UserStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(SignInState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignInUserEvent) {
        when (userEvent) {
            is SignInUserEvent.EmailChanged -> {
                state = state.copy(
                    email = userEvent.email
                )
            }
            is SignInUserEvent.PasswordChanged -> {
                state = state.copy(
                    password = userEvent.password
                )
            }
            is SignInUserEvent.OnSignInClick -> {
                submitSignInData()
            }
            is SignInUserEvent.OnSignUpClick -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
            }
            SignInUserEvent.GoogleSignInButtonClick -> {
                state = state.copy(
                    isGoogleLoading = true,
                    launchGoogleSignIn = true
                )
            }
            is SignInUserEvent.GoogleSignInFailed -> {
                state = state.copy(
                    isGoogleLoading = false,
                    launchGoogleSignIn = false
                )
                sendUiEvent(UiEvent.ShowSnackbar(userEvent.message))
            }
            is SignInUserEvent.GoogleSignInSuccessful -> {
                userEvent.idToken?.let {
                    signInWithGoogleIdToken(it)
                } ?: sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
            }
        }
    }

    private fun submitSignInData() {
        val emailResult = useCases.validateEmail.execute(state.email)
        val passwordResult = useCases.validatePassword.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult
        ).any{ !it.successful }

        if(hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage
            )
            return
        }
        signIn()
    }

    private fun signIn(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.signIn(state.email, state.password).collectLatest{ response ->
                when(response){
                    is Result.Loading ->{
                        sendUiEvent(UiEvent.HideKeyboard)
                    }
                    is Result.Error ->{
                        sendUiEvent(UiEvent.ShowSnackbar(SIGN_IN_UNSUCCESSFUL))
                    }
                    is Result.Success ->{
                        sendUiEvent(UiEvent.ShowSnackbar(SIGN_IN_SUCCESSFUL))
                        sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
                    }
                }
            }
        }
    }

    private fun signInWithGoogleIdToken(idToken: String){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.signInWithGoogleIdToken(idToken).collect{
                when(it){
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(NOT_LOGGED_WITH_GOOGLE_ACCOUNT))
                    }
                    is Result.Loading -> {
                        sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
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
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
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

