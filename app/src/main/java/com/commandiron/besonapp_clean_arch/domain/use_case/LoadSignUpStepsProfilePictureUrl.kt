package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class LoadSignUpStepsProfilePictureUrl(
    private val preferences: Preferences
) {
    operator fun invoke(): String{
        return preferences.loadTemporalSignUpStepsProfilePictureUrl()
    }
}