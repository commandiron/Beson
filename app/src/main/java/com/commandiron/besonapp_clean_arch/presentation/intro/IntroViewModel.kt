package com.commandiron.besonapp_clean_arch.presentation.intro

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.core.Strings.START
import com.commandiron.besonapp_clean_arch.core.Strings.START_NOW
import com.commandiron.besonapp_clean_arch.core.UiEvent
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.presentation.intro.state.IntroState
import com.commandiron.besonapp_clean_arch.ui.theme.Orange
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    val preferences: Preferences,
): ViewModel() {

    var state by mutableStateOf(IntroState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: IntroEvent) {
        when(event){
            is IntroEvent.OnSwipe -> {
                state = if(event.lastPageFlag){
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
            is IntroEvent.OnStartClick -> {
                preferences.saveShouldShowSplashAndIntro(false)
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Success)
                }
            }
        }
    }
}