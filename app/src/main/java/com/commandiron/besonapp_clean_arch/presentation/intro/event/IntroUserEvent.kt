package com.commandiron.besonapp_clean_arch.presentation.intro.event

sealed class IntroUserEvent {
    data class OnSwipe(val lastPageFlag: Boolean): IntroUserEvent()
    object OnStartClick: IntroUserEvent()
}