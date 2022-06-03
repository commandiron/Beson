package com.commandiron.besonapp_clean_arch

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commandiron.besonapp_clean_arch.core.Strings
import com.commandiron.besonapp_clean_arch.domain.model.UserState
import com.commandiron.besonapp_clean_arch.domain.use_case.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel@Inject constructor(
    useCases: UseCases
): ViewModel() {

    var state by mutableStateOf(AppState())
        private set

    private val _uiEvent = Channel<AppUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        viewModelScope.launch {
            delay(timeMillis = state.coldSplashScreenDelay)
            state = state.copy(
                isColdSplashScreenVisible = false
            )
        }
        state = state.copy(
            shouldShowSplashAndIntro = useCases.loadShouldShowSplashAndIntro()
        )
//        viewModelScope.launch {
//            useCases.getUserAuthState().collect{
//                when(it){
//                    UserState.SIGNED_IN -> {
//                        sendUiEvent(AppUiEvent.ShowSnackbar(Strings.SIGN_IN_SUCCESSFUL))
//                        sendUiEvent(AppUiEvent.NavigateToProfileScreen)
//                    }
//                    else -> {}
//                }
//            }
//        }
    }
    private fun sendUiEvent(uiEvent: AppUiEvent){
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }
}

sealed class AppUiEvent {
    data class ShowSnackbar(val message: String): AppUiEvent()
    object NavigateToProfileScreen: AppUiEvent()
}

data class AppState(
    val isColdSplashScreenVisible: Boolean = true,
    val coldSplashScreenDelay: Long = 1000L,
    val shouldShowSplashAndIntro: Boolean = true
)