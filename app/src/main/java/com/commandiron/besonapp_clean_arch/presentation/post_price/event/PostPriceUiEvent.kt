package com.commandiron.besonapp_clean_arch.presentation.post_price.event

sealed class PostPriceUiEvent {
    object CloseKeyboard: PostPriceUiEvent()
    data class ShowSnackBar(val message: String): PostPriceUiEvent()
}
