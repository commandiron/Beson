package com.commandiron.besonapp_clean_arch.core

sealed class UiEvent {
    object HideKeyboard: UiEvent()
    data class NavigateTo(val route: String): UiEvent()
    data class ShowSnackbar(val message: String): UiEvent()
}