package com.commandiron.besonapp_clean_arch.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.ADDED_TO_FAVORITES
import com.commandiron.besonapp_clean_arch.core.Strings.REMOVED_FROM_FAVORITES
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.FavoriteStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    var state by mutableStateOf(DetailsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val userUid: String? = savedStateHandle["userUid"]
        userUid?.let {
            state = state.copy(
                userUid = it
            )
        }
        getUserProfile()
        getUserFavoriteState()
    }

    fun onEvent(userEvent: DetailsUserEvent) {
        when (userEvent) {
            DetailsUserEvent.Back -> {
                sendUiEvent(UiEvent.NavigateUp)
            }
            DetailsUserEvent.Favorite -> {
                if(state.favoriteStatus == FavoriteStatus.ALREADY_IN_FAVORITES){
                    removeFromFavorites()
                }else{
                    addToFavorites()
                }
            }
        }
    }

    private fun getUserProfile(){
        viewModelScope.launch {
            useCases.getUserProfileById(state.userUid).collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        state = state.copy(
                            name = result.data?.name ?: "",
                            phoneNumber = useCases.formatPhoneNumber(result.data?.phoneNumber ?: ""),
                            imageUrl = result.data?.imageUrl ?: ""
                        )
                    }
                }
            }
        }
    }

    private fun getUserFavoriteState(){
        viewModelScope.launch {
            useCases.getUserFavoriteStatus(state.userUid).collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        result.data?.let {
                            state = state.copy(
                                favoriteStatus = it
                            )
                        }
                    }
                }
            }
        }
    }

    private fun addToFavorites(){
        viewModelScope.launch {
            useCases.addToFavorites(state.userUid).collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.ShowSnackbar(ADDED_TO_FAVORITES))
                    }
                }
            }
        }
    }

    private fun removeFromFavorites(){
        viewModelScope.launch {
            useCases.removeFromFavorites(state.userUid).collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.ShowSnackbar(REMOVED_FROM_FAVORITES))
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
