package com.commandiron.besonapp_clean_arch.presentation.intro.event

sealed class IntroEvent {
    data class OnSwipe(val lastPageFlag: Boolean): IntroEvent()
    object OnStartClick: IntroEvent()
}