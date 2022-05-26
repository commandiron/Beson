package com.commandiron.besonapp_clean_arch

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel: ViewModel() {

    private val _isColdSplashScreenVisible: MutableState<Boolean> = mutableStateOf(true)
    val isColdSplashScreenVisible: State<Boolean> = _isColdSplashScreenVisible

    private val coldSplashScreenDelay = 1000L

    init {
        viewModelScope.launch {
            delay(timeMillis = coldSplashScreenDelay)
            _isColdSplashScreenVisible.value = false
        }
    }
}