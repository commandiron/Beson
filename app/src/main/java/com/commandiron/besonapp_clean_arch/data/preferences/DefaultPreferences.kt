package com.commandiron.besonapp_clean_arch.data.preferences

import android.content.SharedPreferences
import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPref: SharedPreferences
): Preferences {
    override fun saveShouldShowSplashAndIntro(shouldShow: Boolean) {
        sharedPref.edit()
            .putBoolean(Preferences.KEY_SHOULD_SHOW_SPLASH_AND_INTRO, shouldShow)
            .apply()
    }

    override fun loadShouldShowSplashAndIntro(): Boolean {
        return sharedPref.getBoolean(
            Preferences.KEY_SHOULD_SHOW_SPLASH_AND_INTRO,
            true
        )
    }
}