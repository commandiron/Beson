package com.commandiron.besonapp_clean_arch.presentation.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.presentation.login.event.LogInUiEvent
import com.commandiron.besonapp_clean_arch.presentation.login.event.LogInUserEvent
import com.commandiron.besonapp_clean_arch.presentation.login.state.LogInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(LogInState())
        private set

    private val _uiEvent = Channel<LogInUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: LogInUserEvent) {
        when (userEvent) {
            is LogInUserEvent.EmailChanged -> {
                state = state.copy(
                    email = userEvent.email
                )
            }
            is LogInUserEvent.PasswordChanged -> {
                state = state.copy(
                    password = userEvent.password
                )
            }
            is LogInUserEvent.OnLogInClick -> {
                submitLogInData()
            }
            is LogInUserEvent.OnSignUpClick -> {
                sendUiEvent(LogInUiEvent.NavigateToSignUp)
            }
        }
    }

    private fun submitLogInData() {
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
        //Eğer firebaseden başarılı olursa
        sendUiEvent(LogInUiEvent.LogInValidationAndFirebaseLogInSuccess)
    }

    private fun sendUiEvent(uiEvent: LogInUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}