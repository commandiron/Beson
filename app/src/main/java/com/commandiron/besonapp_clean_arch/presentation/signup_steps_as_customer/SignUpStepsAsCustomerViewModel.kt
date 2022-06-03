package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpStepsAsCustomerViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsAsCustomerState(
            name = useCases.loadTemporalSignUpStepsName(),
            phoneNumber = useCases.loadTemporalSignUpStepsPhoneNumber()
        )
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignUpStepsAsCustomerUserEvent) {
        when (userEvent) {
            is SignUpStepsAsCustomerUserEvent.OnBackToSignUpClick -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
            }
            is SignUpStepsAsCustomerUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsAsCustomerUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = userEvent.phoneNumber
                )
            }
            is SignUpStepsAsCustomerUserEvent.PictureChanged -> {
                state = state.copy(
                    profilePictureUri= userEvent.uri
                )
            }
            is SignUpStepsAsCustomerUserEvent.NameScreenNext -> {
                useCases.saveTemporalSignUpStepsName(state.name)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCustomer2.route))
            }
            is SignUpStepsAsCustomerUserEvent.PhoneNumberScreenNext -> {
                useCases.saveTemporalSignUpStepsPhoneNumber(state.phoneNumber)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCustomer3.route))
            }
            is SignUpStepsAsCustomerUserEvent.PictureScreenNext -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
