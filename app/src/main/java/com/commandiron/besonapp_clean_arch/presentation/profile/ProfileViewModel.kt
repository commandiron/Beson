package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.DONE_DIALOG_MESSAGE_REMOVED_FROM_FAVORITE
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING_MESSAGE_PRICE_IS_CLEARED
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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
        viewModelScope.launch {
            useCases.getUserProfile().collect{ result ->
                when(result){
                    is Result.Loading -> {
                    }
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        val userProfile = result.data ?: UserProfile()
                        state = state.copy(
                            name = userProfile.name ?: "",
                            imageUrl = userProfile.imageUrl,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onEvent(userEvent: ProfileUserEvent) {
        when (userEvent) {
            is ProfileUserEvent.OnProfileHeaderVerticalDrag -> {
                state = state.copy(
                    profileHeaderHeight = userEvent.profileHeaderHeight
                )
            }
            is ProfileUserEvent.OnEditClick -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.EditProfile.route))
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
                    showDeleteMyUpdateAlertDialog = true
                )
            }
            is ProfileUserEvent.UnFavoriteProfile -> {
                state = state.copy(
                    showUnFavoriteAlertDialog = true
                )
            }
            is ProfileUserEvent.DeleteMyUpdateAlertDialogConfirm -> {
                state = state.copy(
                    showDeleteMyUpdateAlertDialog = false,
                    isLoading = true,
                    loadingMessage = LOADING_MESSAGE_PRICE_IS_CLEARED
                )
                //Sil
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
                //Favoriden Çıkar
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

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}