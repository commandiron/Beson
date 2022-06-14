package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.DONE_DIALOG_MESSAGE_REMOVED_FROM_FAVORITE
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_DELETED
import com.commandiron.besonapp_clean_arch.core.Strings.REMOVED_FROM_FAVORITES
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getUserProfile()
        getMyPrices()
        getAllMyFavorites()
    }

    fun onEvent(userEvent: ProfileUserEvent) {
        when (userEvent) {
            is ProfileUserEvent.OnEditClick -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(
                            route = NavigationItem.EditProfile.route,
                        )
                    )
                )
            }
            ProfileUserEvent.MyUpdatesDropDownIconClick -> {
                state = state.copy(
                    myUpdatesSurfaceExpanded = !state.myUpdatesSurfaceExpanded
                )
            }
            ProfileUserEvent.FavoriteProfilesDropDownIconClick -> {
                state = state.copy(
                    myFavoriteProfilesSurfaceExpanded = !state.myFavoriteProfilesSurfaceExpanded
                )
            }
            is ProfileUserEvent.DeleteMyUpdate -> {
                state = state.copy(
                    showDeleteMyUpdateAlertDialog = true,
                    deletedPriceItem = userEvent.item
                )
            }
            is ProfileUserEvent.UnFavoriteProfile -> {
                state = state.copy(
                    showUnFavoriteAlertDialog = true,
                    deletedFavoriteProfileUid = userEvent.profileUid
                )
            }
            is ProfileUserEvent.DeleteMyUpdateAlertDialogConfirm -> {
                state = state.copy(
                    showDeleteMyUpdateAlertDialog = false,
                )
                deleteMyPrice()
            }
            is ProfileUserEvent.DeleteMyUpdateAlertDialogDismiss -> {
                state = state.copy(
                    showDeleteMyUpdateAlertDialog = false
                )
            }
            is ProfileUserEvent.UnFavoriteAlertDialogConfirm -> {
                state = state.copy(
                    showUnFavoriteAlertDialog = false,
                    showDoneDialog = true,
                    doneDialogMessage = DONE_DIALOG_MESSAGE_REMOVED_FROM_FAVORITE
                )
                removefromFavorites()
            }
            is ProfileUserEvent.UnFavoriteAlertDialogDismiss -> {
                state = state.copy(
                    showUnFavoriteAlertDialog = false
                )
            }
            is ProfileUserEvent.DoneDialogDismiss -> {
                state = state.copy(
                    showDoneDialog = false
                )
            }
        }
    }

    private fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
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
                            userType = result.data?.userType ?: UserType.CUSTOMER
                        )
                    }
                }
            }
        }
    }

    private fun getMyPrices(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getMyPrices().collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        state = state.copy(
                            myPrices = result.data?.sortedByDescending { it.date }
                        )
                    }
                }
            }
        }
    }

    private fun getAllMyFavorites(){
        viewModelScope.launch {
            useCases.getAllMyFavorites().collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        result.data?.let {
                            state = state.copy(
                                favoriteUserProfiles = it
                            )
                        }
                    }
                }
            }
        }
    }

    private fun deleteMyPrice(){
        state.deletedPriceItem?.let {
            viewModelScope.launch {
                useCases.deleteMyPrice(it).collect{ result ->
                    when(result){
                        is Result.Loading -> {}
                        is Result.Error -> {
                            sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                        }
                        is Result.Success -> {
                            sendUiEvent(UiEvent.ShowSnackbar(PRICE_DELETED))
                        }
                    }
                }
            }
        }
    }

    private fun removefromFavorites(){
        state.deletedFavoriteProfileUid?.let {
            viewModelScope.launch {
                useCases.removeFromFavorites(it).collect{ result ->
                    when(result){
                        is Result.Loading -> {}
                        is Result.Error -> {
                            sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                        }
                        is Result.Success -> {}
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