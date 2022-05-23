package com.commandiron.besonapp_clean_arch.presentation.intro

sealed class IntroEvent {
    data class OnSwipe(val lastPageFlag: Boolean): IntroEvent()
    object OnStartClick: IntroEvent()
}