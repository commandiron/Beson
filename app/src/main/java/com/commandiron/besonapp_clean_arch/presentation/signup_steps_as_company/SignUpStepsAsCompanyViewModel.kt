package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

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
class SignUpStepsAsCompanyViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsAsCompanyState(
            name = useCases.loadTemporalSignUpStepsName(),
            phoneNumber = useCases.loadTemporalSignUpStepsPhoneNumber(),
            selectedMainConstructionItem = useCases.loadTemporalSignUpStepsSelectedConsItem()
        )
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: SignUpStepsAsCompanyUserEvent) {
        when (userEvent) {
            is SignUpStepsAsCompanyUserEvent.OnBackToSignUpClick -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
            }
            is SignUpStepsAsCompanyUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsAsCompanyUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = userEvent.phoneNumber
                )
            }
            is SignUpStepsAsCompanyUserEvent.PictureChanged -> {
                state = state.copy(
                    profilePictureUri= userEvent.uri
                )
            }
            is SignUpStepsAsCompanyUserEvent.MainCategorySelected -> {
                state = state.copy(
                    selectedMainConstructionItem = userEvent.itemMain
                )
            }
            is SignUpStepsAsCompanyUserEvent.SubCategoriesSelected -> {
                state = state.copy(
                    selectedSubConstructionItems = userEvent.itemList
                )
            }
            is SignUpStepsAsCompanyUserEvent.NameScreenNext -> {
                useCases.saveTemporalSignUpStepsName(state.name)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany2.route))
            }
            is SignUpStepsAsCompanyUserEvent.PhoneNumberScreenNext -> {
                useCases.saveTemporalSignUpStepsPhoneNumber(state.phoneNumber)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany3.route))
            }
            is SignUpStepsAsCompanyUserEvent.PictureScreenNext -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany4.route))
            }
            is SignUpStepsAsCompanyUserEvent.ConstructionCategoryScreenNext -> {
                if(state.selectedMainConstructionItem == null){
                    //Seçmediniz snack bar
                }else{
                    useCases.saveTemporalSignUpStepsSelectedConsItem(state.selectedMainConstructionItem!!)
                    sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany5.route))
                }
            }
            is SignUpStepsAsCompanyUserEvent.SubConstructionCategoryScreenNext -> {
                if(state.selectedSubConstructionItems == null){
                    //Seçmediniz snack bar
                }else{
                    //Save picture to firebase
                    //Save profile info to firebase
                    sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
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
