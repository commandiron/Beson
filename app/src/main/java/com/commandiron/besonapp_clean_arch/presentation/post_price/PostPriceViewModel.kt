package com.commandiron.besonapp_clean_arch.presentation.post_price

import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.PLEASE_ENTER_PRICE
import com.commandiron.besonapp_clean_arch.core.Strings.PLEASE_SELECT_PRICE_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_SENDING
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.ui.theme.ErrorRed
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen
import com.commandiron.besonapp_clean_arch.ui.theme.PleaseSelectBlue
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostPriceViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(PostPriceState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        if(state.fineLocationPermissionGranted){
            getUserLastKnownPosition()
        }
    }

    fun onEvent(userEvent: PostPriceUserEvent) {
        when (userEvent) {
            is PostPriceUserEvent.SubConsCategorySelectionBoxClick -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.OnSubConstructionCategorySelect -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded,
                    selectedSubConsItemTitle = state.subConsItems[userEvent.selectedIndex!!].title,
                    priceItems = state.subConsItems[userEvent.selectedIndex].priceItems,
                    selectedPriceItemTitle = null,
                    priceTextFieldEnabled = false,
                    price = "",
                    subConsItemBorderColor = NoErrorGreen,
                    priceItemBorderColor = PleaseSelectBlue
                )
            }
            is PostPriceUserEvent.SubConsCategoryDismiss -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.PriceCategorySelectionBoxClick -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded,
                    priceTextFieldEnabled = state.selectedPriceItemTitle != null
                )
            }
            is PostPriceUserEvent.OnPriceCategorySelect -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded,
                    selectedPriceItemTitle = state.priceItems?.get(userEvent.selectedIndex!!)?.title,
                    selectedPriceItemUnit = state.priceItems?.get(userEvent.selectedIndex!!)?.unit,
                    priceTextFieldEnabled = true,
                    priceItemBorderColor =  NoErrorGreen,
                    priceBorderColor = PleaseSelectBlue
                )
            }
            is PostPriceUserEvent.PriceTextFieldClick -> {
                if(state.selectedPriceItemTitle == null){
                    sendUiEvent(UiEvent.ShowSnackbar(PLEASE_SELECT_PRICE_CATEGORY))
                }
            }
            is PostPriceUserEvent.PriceCategoryDismiss -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.PriceChange -> {
                val validatedPrice = useCases.validatePostPriceString(
                    oldValue = state.price,
                    newValue = userEvent.price
                )
                state = state.copy(
                    price = validatedPrice,
                    priceBorderColor = NoErrorGreen
                )
                if(state.price == ""){
                    state = state.copy(
                        priceBorderColor = PleaseSelectBlue
                    )
                }
            }
            is PostPriceUserEvent.KeyboardDone -> {
                sendUiEvent(UiEvent.HideKeyboard)
            }
            is PostPriceUserEvent.PostPrice -> {
                if(state.price.isNotEmpty()){
                    state = state.copy(
                        showAlertDialog = true
                    )
                }else{
                    state = state.copy(
                        priceBorderColor = ErrorRed
                    )
                    sendUiEvent(UiEvent.ShowSnackbar(PLEASE_ENTER_PRICE))
                }
                if(state.selectedSubConsItemTitle == null){
                    state = state.copy(
                        subConsItemBorderColor = ErrorRed
                    )
                }
                if(state.selectedPriceItemTitle == null){
                    state = state.copy(
                        priceItemBorderColor = ErrorRed
                    )
                }
            }
            PostPriceUserEvent.AlertDialogDismiss -> {
                state = state.copy(
                    showAlertDialog = false
                )
            }
            PostPriceUserEvent.AlertDialogConfirm -> {
                state = state.copy(
                    showAlertDialog = false,
                    placeholderIsVisible = true,
                )
                sendUiEvent(UiEvent.ShowLoadingScreen(PRICE_SENDING))
                //Fiyatı Gönder
            }
            PostPriceUserEvent.DoneDialogDismiss -> {
                state = state.copy(
                    placeholderIsVisible = false,
                    priceIsSent = false
                )
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.Profile.route))
            }
            PostPriceUserEvent.FineLocationPermissionDenied -> {
                state = state.copy(
                    fineLocationPermissionGranted = false,
                    fineLocationPermissionRationale = "Konum için izin gerekiyor."
                )
            }
            PostPriceUserEvent.FineLocationPermissionGranted -> {
                state = state.copy(
                    fineLocationPermissionGranted = true,
                    fineLocationPermissionRationale = "Konum izni verildi."
                )
                getUserLastKnownPosition()
            }
        }
    }

    private fun getUserLastKnownPosition(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserLastKnownLocation().collect{ resultLocation ->
                when(resultLocation){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(
                            UiEvent.ShowSnackbar(
                                resultLocation.exception?.message ?: Strings.SORRY_SOMETHING_BAD_HAPPENED
                            )
                        )
                    }
                    is Result.Success -> {
                        getLatLngFromLocation(resultLocation.data)
                        getCityFromLatLng()
                    }
                }
            }
        }
    }

    private fun getLatLngFromLocation(location: Location?){
        location?.let {
            val latLng = useCases.getLatLngFromLocation(it)
            state = state.copy(
                myLatLng = latLng
            )
        }
    }

    private fun getCityFromLatLng(){
        val myLocationCity = useCases.getCityFromLatLng(state.myLatLng)
        state = state.copy(
            myLocationCity = myLocationCity
        )
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(uiEvent)
        }
    }
}
