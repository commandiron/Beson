package com.commandiron.besonapp_clean_arch.presentation.signup_steps

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
class SignUpStepsViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsState(
            name = useCases.loadTemporalSignUpStepsName(),
            phoneNumber = useCases.loadTemporalSignUpStepsPhoneNumber(),
            selectedMainConstructionItem = useCases.loadTemporalSignUpStepsSelectedConsItem()
        )
    )
        private set

    private val _uiEvent = Channel<SignUpStepsUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignUpStepsUserEvent) {
        when (userEvent) {
            is SignUpStepsUserEvent.OnBackToSignUpClick -> {
                sendUiEvent(SignUpStepsUiEvent.NavigateToSignUpScreen)
            }
            is SignUpStepsUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = userEvent.phoneNumber
                )
            }
            is SignUpStepsUserEvent.PictureChanged -> {
                state = state.copy(
                    profilePictureUri= userEvent.uri
                )
            }
            is SignUpStepsUserEvent.MainCategorySelected -> {
                state = state.copy(
                    selectedMainConstructionItem = userEvent.itemMain
                )
            }
            is SignUpStepsUserEvent.SubCategoriesSelected -> {
                state = state.copy(
                    selectedSubConstructionItems = userEvent.itemList
                )
            }
            is SignUpStepsUserEvent.NameScreenNext -> {
                useCases.saveTemporalSignUpStepsName(state.name)
                sendUiEvent(SignUpStepsUiEvent.NavigateToNextScreen)
            }
            is SignUpStepsUserEvent.PhoneNumberScreenNext -> {
                useCases.saveTemporalSignUpStepsPhoneNumber(state.phoneNumber)
                sendUiEvent(SignUpStepsUiEvent.NavigateToNextScreen)
            }
            is SignUpStepsUserEvent.PictureScreenNext -> {
                sendUiEvent(SignUpStepsUiEvent.NavigateToNextScreen)
            }
            is SignUpStepsUserEvent.ConstructionCategoryScreenNext -> {
                if(state.selectedMainConstructionItem == null){
                    //Seçmediniz snack bar
                }else{
                    useCases.saveTemporalSignUpStepsSelectedConsItem(state.selectedMainConstructionItem!!)
                    sendUiEvent(SignUpStepsUiEvent.NavigateToNextScreen)
                }
            }
            is SignUpStepsUserEvent.SubConstructionCategoryScreenNext -> {
                if(state.selectedSubConstructionItems == null){
                    //Seçmediniz snack bar
                }else{
                    //Save picture to firebase
                    //Save profile info to firebase
                    sendUiEvent(SignUpStepsUiEvent.RegistrationSuccess)
                }
            }
        }
    }

    private fun sendUiEvent(uiEvent: SignUpStepsUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
