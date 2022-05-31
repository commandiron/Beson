package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class LoadShouldShowSplashAndIntro(
    private val preferences: Preferences
) {
    operator fun invoke() : Boolean{
        return preferences.loadShouldShowSplashAndIntro()
    }
}