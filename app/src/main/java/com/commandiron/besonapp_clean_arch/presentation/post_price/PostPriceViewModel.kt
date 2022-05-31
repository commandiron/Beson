package com.commandiron.besonapp_clean_arch.presentation.post_price

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
class PostPriceViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(PostPriceState())
        private set

    private val _uiEvent = Channel<PostPriceUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: PostPriceUserEvent) {
        when (userEvent) {
            is PostPriceUserEvent.OnSubConstructionCategoryBoxClick -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.OnSubConstructionCategorySelect -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded,
                    selectedSubConsItemTitle = state.subConsItems[userEvent.selectedIndex!!].title,
                    priceItems = state.subConsItems[userEvent.selectedIndex].priceItems,
                    selectedPriceItemTitle = null
                )
            }
            is PostPriceUserEvent.OnSubConstructionCategoryDismiss -> {
                state = state.copy(
                    subConsCategoryDropDownMenuIsExpanded = !state.subConsCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.OnPriceCategoryBoxClick -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded
                )
            }
            is PostPriceUserEvent.OnPriceCategorySelect -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded,
                    selectedPriceItemTitle = state.priceItems?.get(userEvent.selectedIndex!!)?.title
                )
            }
            is PostPriceUserEvent.OnPriceCategoryDismiss -> {
                state = state.copy(
                    priceCategoryDropDownMenuIsExpanded = !state.priceCategoryDropDownMenuIsExpanded
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
