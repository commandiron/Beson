package com.commandiron.besonapp_clean_arch.core

import com.commandiron.besonapp_clean_arch.navigation.NavigationOptions

sealed class UiEvent {
    object HideKeyboard: UiEvent()
    data class NavigateTo(val navigationOptions: NavigationOptions): UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: String): UiEvent()
    data class ShowLoadingScreen(val message: String): UiEvent()
    object HideLoadingScreen: UiEvent()
}