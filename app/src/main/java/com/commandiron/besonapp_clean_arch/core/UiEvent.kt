package com.commandiron.besonapp_clean_arch.core

sealed class UiEvent {
    object Success: UiEvent()
    object NavigateUp: UiEvent()
    data class ShowSnackbar(val message: String): UiEvent()
}
