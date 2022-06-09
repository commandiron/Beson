package com.commandiron.besonapp_clean_arch.presentation.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Strings.START
import com.commandiron.besonapp_clean_arch.core.Strings.START_NOW
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import com.commandiron.besonapp_clean_arch.navigation.NavigationItem
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.ui.theme.Orange
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(IntroState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(userEvent: IntroUserEvent) {
        when(userEvent){
            is IntroUserEvent.OnSwipe -> {
                state = if(userEvent.lastPageFlag){
                    state.copy(
                        footerColor = Orange,
                        bottomBarText = START
                    )
                }else{
                    state.copy(
                        footerColor = PrimaryColor,
                        bottomBarText = START_NOW
                    )
                }
            }
            is IntroUserEvent.OnStartClick -> {
                useCases.saveShouldShowSplashAndIntro(false)
                sendUiEvent(UiEvent.NavigateTo(NavigationItem.SignUp.route))
            }
        }
    }

    private fun sendUiEvent(uiEvent: UiEvent){
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(uiEvent)
        }
    }
}