package com.commandiron.besonapp_clean_arch.presentation.splash

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _isSplashScreenVisible: MutableState<Boolean> = mutableStateOf(true)
    val isSplashScreenVisible: State<Boolean> = _isSplashScreenVisible

    private val splashScreenDelay = 1000L

    init {
        viewModelScope.launch {
            delay(timeMillis = splashScreenDelay)
            _isSplashScreenVisible.value = false
        }
    }
}