package com.commandiron.besonapp_clean_arch.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.core.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
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
            viewModelScope.launch {
                useCases.getUserProfileById(it).collect{ result ->
                    when(result){
                        is Result.Loading -> {}
                        is Result.Error -> {}
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
    }

    fun onEvent(userEvent: DetailsUserEvent) {
        when (userEvent) {
            DetailsUserEvent.Back -> {
                sendUiEvent(UiEvent.NavigateUp)
            }
            DetailsUserEvent.Favorite -> {

            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(uiEvent)
        }
    }
}
