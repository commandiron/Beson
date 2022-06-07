package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class SaveSignUpStepsProfilePictureUrl(
    private val preferences: Preferences
) {
    operator fun invoke(url: String){
        preferences.saveTemporalSignUpStepsProfilePictureUrl(url)
    }
}