package com.commandiron.besonapp_clean_arch.presentation.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.presentation.login.event.LogInUiEvent
import com.commandiron.besonapp_clean_arch.presentation.profile.event.ProfileUiEvent
import com.commandiron.besonapp_clean_arch.presentation.profile.event.ProfileUserEvent
import com.commandiron.besonapp_clean_arch.presentation.profile.state.ProfileState
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

    private val _uiEvent = Channel<ProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: ProfileUserEvent) {
        when (userEvent) {
            is ProfileUserEvent.OnProfileHeaderVerticalDrag -> {
                state = state.copy(
                    profileHeaderHeight = userEvent.profileHeaderHeight
                )
            }
            is ProfileUserEvent.OnEditClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToEditProfile)
            }
            is ProfileUserEvent.OnMyUpdatesClick -> {
                sendUiEvent(ProfileUiEvent.NavigateToMyPriceUpdates)
            }

        }
    }

    private fun sendUiEvent(uiEvent: ProfileUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}