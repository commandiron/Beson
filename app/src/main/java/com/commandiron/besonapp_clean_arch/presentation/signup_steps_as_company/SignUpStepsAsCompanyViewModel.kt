package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.NOT_SELECTED_MAIN_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.NOT_SELECTED_SUB_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpStepsAsCompanyViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(
        SignUpStepsAsCompanyState(
            name = useCases.loadSignUpStepsName(),
            phoneNumber = useCases.loadSignUpStepsPhoneNumber(),
            selectedMainConstructionItem = useCases.loadSignUpStepsSelectedConsItem()
        )
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            useCases.updateUserProfile(UserProfile(userType = UserType.COMPANY)).collect()
        }
    }

    fun onEvent(userEvent: SignUpStepsAsCompanyUserEvent) {
        when (userEvent) {
            is SignUpStepsAsCompanyUserEvent.NameChanged -> {
                state = state.copy(
                    name = userEvent.name
                )
            }
            is SignUpStepsAsCompanyUserEvent.PhoneNumberChanged -> {
                state = state.copy(
                    phoneNumber = useCases.validatePhoneNumber(state.phoneNumber, userEvent.phoneNumber)
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
                useCases.saveSignUpStepsName(state.name)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany2.route))
            }
            is SignUpStepsAsCompanyUserEvent.PhoneNumberScreenNext -> {
                useCases.saveSignUpStepsPhoneNumber(state.phoneNumber)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany3.route))
            }
            is SignUpStepsAsCompanyUserEvent.PictureScreenNext -> {
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany4.route))
            }
            is SignUpStepsAsCompanyUserEvent.ConstructionCategoryScreenNext -> {
                if(state.selectedMainConstructionItem == null){
                    sendUiEvent(UiEvent.ShowSnackbar(NOT_SELECTED_MAIN_CONSTRUCTION_CATEGORY))
                }else{
                    useCases.saveSignUpStepsSelectedConsItemId(state.selectedMainConstructionItem!!.id)
                    sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUpStepsAsCompany5.route))
                }
            }
            is SignUpStepsAsCompanyUserEvent.SubConstructionCategoryScreenNext -> {
                if(state.selectedSubConstructionItems == null){
                    sendUiEvent(UiEvent.ShowSnackbar(NOT_SELECTED_SUB_CONSTRUCTION_CATEGORY))
                }else{
                    viewModelScope.launch {
                        useCases.updateUserProfile(
                            UserProfile(
                                name = state.name,
                                phoneNumber = state.phoneNumber,
                                userType = UserType.COMPANY,
                                selectedMainConstructionItem = state.selectedMainConstructionItem,
                                selectedSubConstructionItems = state.selectedSubConstructionItems
                            )
                        ).collect{ result ->
                            when(result){
                                is Result.Loading -> {
                                    //Yükleniyor
                                }
                                is Result.Error -> {
                                    //Bir hata oluştu
                                }
                                is Result.Success -> {
                                    sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
                                }
                            }
                        }
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