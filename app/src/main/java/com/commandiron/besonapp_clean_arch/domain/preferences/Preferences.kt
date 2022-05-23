package com.commandiron.besonapp_clean_arch.domain.preferences

interface Preferences {
    fun saveShouldShowSplashAndIntro(shouldShow: Boolean)
    fun loadShouldShowSplashAndIntro(): Boolean

    companion object {
        const val KEY_SHOULD_SHOW_SPLASH_AND_INTRO = "should_show_splash_and_intro"
    }
}