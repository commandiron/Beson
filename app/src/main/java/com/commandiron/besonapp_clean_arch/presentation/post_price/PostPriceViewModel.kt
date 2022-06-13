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
import com.commandiron.besonapp_clean_arch.core.Strings.PLEASE_SELECT_CONSTRUCTION_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.PLEASE_SELECT_PRICE_CATEGORY
import com.commandiron.besonapp_clean_arch.core.Strings.PRICE_SENDING
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.presentation.model.PriceItem
import com.commandiron.besonapp_clean_arch.ui.theme.ErrorRed
import com.commandiron.besonapp_clean_arch.ui.theme.NoErrorGreen
import com.commandiron.besonapp_clean_arch.ui.theme.PleaseSelectBlue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
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
        getUserProfile()
        if(state.fineLocationPermissionGranted){
            getUserLastKnownPosition()
        }
    }

    fun onEvent(userEvent: PostPriceUserEvent) {
        when (userEvent) {
            is PostPriceUserEvent.SubConsCategorySelectionBoxClick -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded,
                    subConsCategoryBorderColor = PleaseSelectBlue
                )
            }
            is PostPriceUserEvent.OnSubConstructionCategorySelect -> {
                state.subConsItems?.let {
                    state = state.copy(
                        subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded,
                        selectedSubConsItem = it[userEvent.selectedIndex],
                        selectedPriceTitle = null,
                        priceItems = it[userEvent.selectedIndex].priceCategories,
                        priceTextFieldEnabled = false,
                        subConsCategoryBorderColor = NoErrorGreen,
                        priceCategoryBorderColor = PleaseSelectBlue
                    )
                }
            }
            is PostPriceUserEvent.SubConsCategoryDismiss -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.PriceCategorySelectionBoxClick -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded,
                    priceTextFieldEnabled = state.selectedPriceCategoryId != null
                )
            }
            is PostPriceUserEvent.OnPriceCategorySelect -> {
                state.priceItems?.let {
                    state = state.copy(
                        priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded,
                        selectedPriceCategoryId = it[userEvent.selectedIndex].priceCategoryId,
                        selectedPriceTitle = it[userEvent.selectedIndex].title,
                        selectedPriceUnit = it[userEvent.selectedIndex].unit,
                        priceTextFieldEnabled = true,
                        priceCategoryBorderColor =  NoErrorGreen,
                        priceTextFieldBorderColor = PleaseSelectBlue
                    )
                }
            }
            is PostPriceUserEvent.PriceTextFieldClick -> {
                if(state.selectedSubConsItem == null){
                    sendUiEvent(UiEvent.ShowSnackbar(PLEASE_SELECT_CONSTRUCTION_CATEGORY))
                }else if(state.selectedPriceCategoryId == null){
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
                    priceTextFieldBorderColor = NoErrorGreen
                )
                if(state.price == ""){
                    state = state.copy(
                        priceTextFieldBorderColor = PleaseSelectBlue
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
                        priceTextFieldBorderColor = ErrorRed
                    )
                    sendUiEvent(UiEvent.ShowSnackbar(PLEASE_ENTER_PRICE))
                    if(state.selectedPriceCategoryId == null){
                        state = state.copy(
                            priceCategoryBorderColor = ErrorRed
                        )
                    }
                    if(state.selectedSubConsItem == null){
                        state = state.copy(
                            subConsCategoryBorderColor = ErrorRed
                        )
                    }
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
                postPrice()
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

    private fun getUserProfile(){
        viewModelScope.launch(Dispatchers.IO) {
            useCases.getUserProfile().collect{ result ->
                when(result){
                    is Result.Loading -> {}
                    is Result.Error -> {
                        sendUiEvent(UiEvent.ShowSnackbar(Strings.SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        state = state.copy(
                            userUid = result.data?.userUid ?: "",
                            userName = result.data?.name ?: "",
                            subConsItems = result.data?.selectedSubConstructionItems
                        )
                    }
                }
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
            location = myLocationCity
        )
    }

    private fun postPrice(){
        val hasNullField = listOf(
            state.selectedPriceCategoryId,
            state.selectedPriceTitle,
            state.selectedPriceUnit,
            state.price
        ).any { it == null }

        if(hasNullField){
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            useCases.postPrice(
                PriceItem(
                    subConstructionCategoryId = state.selectedSubConsItem!!.id,
                    priceCategoryId = state.selectedPriceCategoryId!!,
                    title = state.selectedPriceTitle!!,
                    unit = state.selectedPriceUnit!!,
                    price = state.price,
                    location = state.location,
                    date = System.currentTimeMillis(),
                    userByName = state.userName,
                    userByUid = state.userUid
                )
            ).collect{ result ->
                when(result){
                    is Result.Loading -> {
                        sendUiEvent(UiEvent.ShowLoadingScreen(PRICE_SENDING))
                    }
                    is Result.Error -> {
                        sendUiEvent(UiEvent.HideLoadingScreen)
                    }
                    is Result.Success -> {
                        delay(2000) //Fake delay for user
                        sendUiEvent(UiEvent.HideLoadingScreen)
                        state = state.copy(
                            priceIsSent = true
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
