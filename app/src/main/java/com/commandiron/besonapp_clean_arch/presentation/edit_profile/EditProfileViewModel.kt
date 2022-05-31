package com.commandiron.besonapp_clean_arch.presentation.edit_profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        EditProfileState(
            imageUrl = "", //Firebase usecase
            name = "", //Firebase usecase
            phoneNumber = "", //Firebase usecase
//            selectedMainConstructionItem = null, //Firebase usecase
//            selectedSubConstructionItems = null //Firebase usecase
        )
    )
        private set

    private val _uiEvent = Channel<EditProfileUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: EditProfileUserEvent) {
        when (userEvent) {
            is EditProfileUserEvent.Cancel -> {
                sendUiEvent(EditProfileUiEvent.ProfileUpdateCanceled)
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
                //Save to Firebase
                sendUiEvent(EditProfileUiEvent.ProfileUpdateSuccess)
            }
        }
    }

    private fun sendUiEvent(uiEvent: EditProfileUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
