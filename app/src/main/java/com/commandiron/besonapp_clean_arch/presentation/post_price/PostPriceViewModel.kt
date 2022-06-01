package com.commandiron.besonapp_clean_arch.presentation.post_price

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.presentation.post_price.event.PostPriceUiEvent
import com.commandiron.besonapp_clean_arch.presentation.post_price.event.PostPriceUserEvent
import com.commandiron.besonapp_clean_arch.presentation.post_price.state.PostPriceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
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

    private val _uiEvent = Channel<PostPriceUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

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
                    price = ""
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
                    priceTextFieldEnabled = true
                )
            }
            is PostPriceUserEvent.PriceTextFieldClick -> {
                if(state.selectedPriceItemTitle == null){
                    sendUiEvent(PostPriceUiEvent.ShowSnackBar("Lütfen Fiyat Kategorisi Seçiniz."))
                }
            }
            is PostPriceUserEvent.PriceCategoryDismiss -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.PriceChange -> {
                val validatedPrice = useCases.validatePostPriceString(state.price, userEvent.price)
                state = state.copy(
                    price = validatedPrice
                )
            }
            is PostPriceUserEvent.KeyboardDone -> {
                sendUiEvent(PostPriceUiEvent.CloseKeyboard)
            }
            is PostPriceUserEvent.PostPrice -> {
                if(state.price.isNotEmpty()){
                    state = state.copy(
                        showAlertDialog = true
                    )
                }else{
                    sendUiEvent(PostPriceUiEvent.ShowSnackBar("Fiyat Giriniz."))
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
                    isLoading = true
                )
                viewModelScope.launch {
                    useCases.pushPrice().onSuccess {
                        state = state.copy(
                            placeholderIsVisible = true,
                            isLoading = false,
                            priceIsSent = true
                        )
                    }
                }
                //Fiyatı Gönder
            }
            PostPriceUserEvent.PriceSentDialogDismiss -> {
                state = state.copy(
                    placeholderIsVisible = false,
                    isLoading = false,
                    priceIsSent = false
                )
            }
        }
    }

    private fun sendUiEvent(uiEvent: PostPriceUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}
