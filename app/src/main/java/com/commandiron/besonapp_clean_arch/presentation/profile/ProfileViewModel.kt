package com.commandiron.besonapp_clean_arch.presentation.profile

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
class ProfileViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(ProfileState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                    loadingMessage = "Fiyat siliniyor..."
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
                    doneDialogMessage = "Favoriden Çıkartıldı."
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