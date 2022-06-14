package com.commandiron.besonapp_clean_arch.presentation.prices

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Result
import com.commandiron.besonapp_clean_arch.core.Strings.SORRY_SOMETHING_BAD_HAPPENED
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PricesViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(PricesState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getPrices()
    }

    fun onEvent(userEvent: PricesUserEvent) {
        when (userEvent) {
            is PricesUserEvent.CategoryClick -> {
                state.allPriceItems?.let { allPriceItems ->
                    state = state.copy(
                        selectedCategoryId = userEvent.id,
                        filteredPriceItems = useCases
                            .filterSearchResults(
                                state.searchText,
                                userEvent.id,
                                allPriceItems
                            )
                    )
                }
            }
            is PricesUserEvent.DetailClick -> {
                sendUiEvent(
                    UiEvent.NavigateTo(
                        NavigationOptions(
                            NavigationItem.Details.withArgs(userEvent.item.userByUid)
                        )
                    )
                )
            }
            is PricesUserEvent.SearchChange -> {
                state = state.copy(
                    searchText = userEvent.text
                )
                state.allPriceItems?.let { allPriceItems ->
                    state = state.copy(
                        searchText = userEvent.text,
                        filteredPriceItems = useCases
                            .filterSearchResults(
                                userEvent.text,
                                state.selectedCategoryId,
                                allPriceItems
                            )
                    )
                }
            }
            PricesUserEvent.SwipeRefresh -> {
                getPrices()
            }
        }
    }

    private fun getPrices(){
        viewModelScope.launch {
            useCases.getPrices().collect{ result ->
                when(result){
                    is Result.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                        sendUiEvent(UiEvent.ShowSnackbar(SORRY_SOMETHING_BAD_HAPPENED))
                    }
                    is Result.Success -> {
                        state = state.copy(
                            allPriceItems = result.data,
                            filteredPriceItems = result.data?.sortedByDescending { it.date }
                        )
                        delay(1000) //Fake delay for user
                        state = state.copy(
                            isLoading = false
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
