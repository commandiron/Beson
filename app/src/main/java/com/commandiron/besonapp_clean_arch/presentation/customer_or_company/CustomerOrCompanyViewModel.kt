package com.commandiron.besonapp_clean_arch.presentation.customer_or_company

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.REGISTER_AS_COMPANY_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.Strings.REGISTER_AS_CUSTOMER_ARE_YOU_SURE
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
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
class CustomerOrCompanyViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(CustomerOrCompanyState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: CustomerOrCompanyUserEvent) {
        when (userEvent) {
            CustomerOrCompanyUserEvent.AlertDialogConfirm -> {
                state = state.copy(
                    showAlertDialog = false
                )
                updateUserProfile()
            }
            CustomerOrCompanyUserEvent.AlertDialogDismiss -> {
                state = state.copy(
                    showAlertDialog = false
                )
            }
            CustomerOrCompanyUserEvent.CompanyClick -> {
                state = state.copy(
                    userType = UserType.COMPANY,
                    showAlertDialog = true,
                    alertDialogTitle = REGISTER_AS_COMPANY_ARE_YOU_SURE
                )
            }
            CustomerOrCompanyUserEvent.CustomerClick -> {
                state = state.copy(
                    userType = UserType.CUSTOMER,
                    showAlertDialog = true,
                    alertDialogTitle = REGISTER_AS_CUSTOMER_ARE_YOU_SURE
                )
            }
        }
    }

    private fun updateUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.updateUserProfile.invoke(UserProfile(userType = state.userType)).collect{
                when(it){
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Loading -> {}
                    is Result.Success -> {
                        when(state.userType){
                            UserType.CUSTOMER -> {
                                sendUiEvent(
                                    UiEvent.NavigateTo(
                                        NavigationOptions(NavigationItem.SignUpStepsAsCustomer1.route)
                                    )
                                )
                            }
                            UserType.COMPANY -> {
                                sendUiEvent(
                                    UiEvent.NavigateTo(
                                        NavigationOptions(NavigationItem.SignUpStepsAsCompany1.route)
                                    )
                                )
                            }
                            null -> {}
                        }

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
