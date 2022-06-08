package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING
import com.commandiron.besonapp_clean_arch.core.Strings.SIGN_OUT_SUCCESSFUL
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.core.Strings.YOUR_PROFILE_UPDATED
import com.commandiron.besonapp_clean_arch.presentation.model.UserState
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.SubConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(EditProfileState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            useCases.getUserProfile().collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        state = state.copy(
                            name = result.data?.name ?: "",
                            phoneNumber = result.data?.phoneNumber ?: "",
                            imageUrl = result.data?.imageUrl ?: "",
                            userType = result.data?.userType,
                            selectedMainConstructionItem = result.data?.selectedMainConstructionItem,
                            selectedSubConstructionItems = result.data?.selectedSubConstructionItems
                        )
                    }

                }
            }
        }
    }

    fun onEvent(userEvent: EditProfileUserEvent) {
        when (userEvent) {
            is EditProfileUserEvent.Cancel -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
            }
            is EditProfileUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name

                )
            }
            is EditProfileUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = userEvent.phoneNumber
                )
            }
            is EditProfileUserEvent.PictureChanged -> {
                state = state.copy(
                    newPictureUri = userEvent.uri
                )
            }
            is EditProfileUserEvent.Save -> {
                state.newPictureUri?.let { uri ->
                    viewModelScope.launch {
                        useCases.uploadProfilePicture(uri).collect{ result ->
                            when(result){
                                is Result.Loading -> {
                                    sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
                                }
                                is Result.Error -> {
                                    sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                                }
                                is Result.Success -> {
                                    state = state.copy(
                                        imageUrl = result.data
                                    )
                                    updateUserProfile()
                                }
                            }
                        }
                    }
                } ?: updateUserProfile()
            }
            EditProfileUserEvent.LogOut -> {
                useCases.signOut()
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
            }
        }
    }

    private fun updateUserProfile(){
        viewModelScope.launch {
            useCases.updateUserProfile(
                UserProfile(
                    name = state.name,
                    phoneNumber = state.phoneNumber,
                    imageUrl = state.imageUrl,
                    selectedMainConstructionItem = state.selectedMainConstructionItem,
                    selectedSubConstructionItems = state.selectedSubConstructionItems
                )
            ).collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(YOUR_PROFILE_UPDATED))
                        sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
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
