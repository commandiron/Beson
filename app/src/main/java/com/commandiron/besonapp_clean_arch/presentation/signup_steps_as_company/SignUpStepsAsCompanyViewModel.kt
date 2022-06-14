package com.commandiron.besonapp_clean_arch.presentation.signup_steps_as_company

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.LOADING
import com.commandiron.besonapp_clean_arch.core.Strings.NOT_SELECTED_MAIN_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.NOT_SELECTED_SUB_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import com.commandiron.besonapp_clean_arch.presentation.model.UserProfile
import com.commandiron.besonapp_clean_arch.presentation.signup.model.UserType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
            profilePictureUrl = useCases.loadSignUpStepsProfilePictureUrl(),
            selectedMainConstructionItem = useCases.loadSignUpStepsSelectedConsItem()
        )
    )
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
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
                    phoneNumber = useCases.validatePhoneNumber(
                        oldValue = state.phoneNumber ?: "",
                        newValue = userEvent.phoneNumber
                    )
                )
            }
            is SignUpStepsAsCompanyUserEvent.PictureChanged -> {
                state = state.copy(
                    selectedPictureUri= userEvent.uri
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
                state.name?.let {
                    useCases.saveSignUpStepsName(it)
                }
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCompany2.route)
                    )
                )
            }
            is SignUpStepsAsCompanyUserEvent.PhoneNumberScreenNext -> {
                state.phoneNumber?.let {
                    useCases.saveSignUpStepsPhoneNumber(it)
                }
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCompany3.route)
                    )
                )
            }
            is SignUpStepsAsCompanyUserEvent.PictureScreenNext -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(NavigationItem.SignUpStepsAsCompany4.route)
                    )
                )
                state.selectedPictureUri?.let { uri ->
                    uploadProfilePicture(uri)
                }
            }
            is SignUpStepsAsCompanyUserEvent.ConstructionCategoryScreenNext -> {
                if(state.selectedMainConstructionItem == null){
                    sendUiEvent(UiEvent.ShowSnackbar(NOT_SELECTED_MAIN_CONSTRUCTION_CATEGORY))
                }else{
                    useCases.saveSignUpStepsSelectedConsItemId(state.selectedMainConstructionItem!!.id)
                    sendUiEvent(
                        UiEvent.NavigateTo(
                            NavigationOptions(NavigationItem.SignUpStepsAsCompany5.route)
                        )
                    )
                }
            }
            is SignUpStepsAsCompanyUserEvent.SubConstructionCategoryScreenNext -> {
                if(state.selectedSubConstructionItems == null){
                    sendUiEvent(UiEvent.ShowSnackbar(NOT_SELECTED_SUB_CONSTRUCTION_CATEGORY))
                }else{
                    updateUserProfile()
                }
            }
        }
    }

    private fun uploadProfilePicture(uri: Uri){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.uploadProfilePicture(uri).collect{ result ->
                when(result){
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(Strings.SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Loading -> {

                    }
                    is Result.Success -> {
                        useCases.saveSignUpStepsProfilePictureUrl(result.data ?: "")
                    }
                }
            }
        }
    }

    private fun updateUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.updateUserProfile(
                UserProfile(
                    name = state.name,
                    phoneNumber = state.phoneNumber,
                    imageUrl = state.profilePictureUrl,
                    userType = UserType.COMPANY,
                    selectedMainConstructionItem = state.selectedMainConstructionItem,
                    selectedSubConstructionItems = state.selectedSubConstructionItems
                )
            ).collect{ result ->
                when(result){
                    is Result.Loading -> {
                        sendUiEvent(UiEvent.ShowLoadingScreen(LOADING))
                    }
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(UiEvent.ShowSnackbar(Strings.SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        sendUiEvent(
                            UiEvent.NavigateTo(
                                NavigationOptions(NavigationItem.Profile.route)
                            )
                        )
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
