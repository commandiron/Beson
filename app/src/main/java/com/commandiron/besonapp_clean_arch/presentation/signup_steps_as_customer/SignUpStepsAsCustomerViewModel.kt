package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_customer

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpStepsAsCustomerViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsAsCustomerState(
            name = useCases.loadSignUpStepsName(),
            phoneNumber = useCases.loadSignUpStepsPhoneNumber()
        )
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            useCases.updateUserProfile(UserProfile(userType = UserType.CUSTOMER)).collect()
        }
    }

    fun onEvent(userEvent: SignUpStepsAsCustomerUserEvent) {
        when (userEvent) {
            is SignUpStepsAsCustomerUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsAsCustomerUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = useCases.validatePhoneNumber(
                        oldValue = state.phoneNumber ?: "",
                        newValue = userEvent.phoneNumber
                    )
                )
            }
            is SignUpStepsAsCustomerUserEvent.PictureChanged -> {
                state = state.copy(
                    selectedPictureUri= userEvent.uri
                )
            }
            is SignUpStepsAsCustomerUserEvent.NameScreenNext -> {
                state.name?.let {
                    useCases.saveSignUpStepsName(it)
                }

                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCustomer2.route)
                    )
                )
            }
            is SignUpStepsAsCustomerUserEvent.PhoneNumberScreenNext -> {
                state.phoneNumber?.let {
                    useCases.saveSignUpStepsPhoneNumber(it)
                }
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCustomer3.route)
                    )
                )
            }
            is SignUpStepsAsCustomerUserEvent.PictureScreenNext -> {
                state.selectedPictureUri?.let { uri ->
                    uploadProfilePicture(uri)
                } ?: updateUserProfile()
            }
        }
    }

    private fun updateUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.updateUserProfile(
                UserProfile(
                    name = state.name,
                    phoneNumber = state.phoneNumber,
                    imageUrl = state.profilePictureUrl,
                    userType = UserType.CUSTOMER
                )
            ).collect{ result ->
                when(result){
                    is Result.Loading -> {
                        sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
                    }
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.NavigateTo(NavigationOptions(NavigationItem.Profile.route)))
                    }
                }
            }
        }
    }

    private fun uploadProfilePicture(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.uploadProfilePicture(uri).collect{ result ->
                when(result){
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Loading -> {
                        sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        state = state.copy(
                            profilePictureUrl = result.data
                        )
                        updateUserProfile()
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
