package com.commandiron.besonapp_clean_arch.presentation.signup_steps

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpStepsViewModel @Inject constructor(
    private val preferences: Preferences,
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsState(
            name = preferences.loadTemporalSignUpStepsName(),
            phoneNumber = preferences.loadTemporalSignUpStepsPhoneNumber(),
            profilePictureUri = preferences.loadTemporalSignUpStepsPictureUriString()?.toUri()
        )
    )
        private set

    private val _uiEvent = Channel<SignUpStepsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignUpStepsUserEvent) {
        when (userEvent) {
            is SignUpStepsUserEvent.OnBackToSignUpClick -> {
                sendUiEvent(SignUpStepsUiEvent.NavigateToSignUpScreen)
            }
            is SignUpStepsUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = userEvent.phoneNumber
                )
            }
            is SignUpStepsUserEvent.PictureChanged -> {
                state = state.copy(
                    profilePictureUri = userEvent.uri
                )
            }
            is SignUpStepsUserEvent.OnNextClick1 -> {
                preferences.saveTemporalSignUpStepsName(state.name)
                sendUiEvent(SignUpStepsUiEvent.NavigateToSignUpStepsScreen2)
            }
            is SignUpStepsUserEvent.OnNextClick2 -> {
                preferences.saveTemporalSignUpStepsPhoneNumber(state.phoneNumber)
                sendUiEvent(SignUpStepsUiEvent.RegistrationSuccess)
            }
            is SignUpStepsUserEvent.OnCompleteClick -> {
                preferences.saveTemporalSignUpStepsPictureUriString(
                    state.profilePictureUri.toString()
                )
                //Burda firebase kaydı yapılacak ve başarılı olursa gidilecek.
                sendUiEvent(SignUpStepsUiEvent.RegistrationSuccess)
            }
        }
    }

    private fun sendUiEvent(uiEvent: SignUpStepsUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
