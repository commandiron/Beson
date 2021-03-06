package com.commandiron.besonapp_clean_arch.data.preferences

import android.content.SharedPreferences
import android.net.Uri
import androidx.core.net.toUri
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

    override fun saveTemporalSignUpStepsName(name: String) {
        sharedPref.edit()
            .putString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_NAME, name)
            .apply()
    }

    override fun loadTemporalSignUpStepsName(): String {
        return sharedPref.getString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_NAME, "").toString()
    }

    override fun saveTemporalSignUpStepsPhoneNumber(phoneNumber: String) {
        sharedPref.edit()
            .putString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_PHONE_NUMBER, phoneNumber)
            .apply()
    }

    override fun loadTemporalSignUpStepsPhoneNumber(): String {
        return sharedPref.getString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_PHONE_NUMBER, "").toString()
    }

    override fun saveTemporalSignUpStepsProfilePictureUrl(url: String) {
        sharedPref.edit()
            .putString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_URL,url)
            .apply()
    }

    override fun loadTemporalSignUpStepsProfilePictureUrl(): String {
        return sharedPref.getString(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_URL, "").toString()
    }

    override fun saveTemporalSignUpStepsSelectedConsItemId(id: Int) {
        sharedPref.edit()
            .putInt(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_CONS_ITEM_ID, id)
            .apply()
    }

    override fun loadTemporalSignUpStepsSelectedConsItemId(): Int {
        return sharedPref.getInt(Preferences.KEY_TEMPORAL_SIGN_UP_STEPS_CONS_ITEM_ID, 0)

    }
}