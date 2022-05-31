package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class SaveShouldShowSplashAndIntro(
    private val preferences: Preferences
) {
    operator fun invoke(shouldShow: Boolean){
        preferences.saveShouldShowSplashAndIntro(shouldShow)
    }
}