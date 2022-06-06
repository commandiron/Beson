package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class SaveSignUpStepsName(
    private val preferences: Preferences
) {
    operator fun invoke(name: String){
        preferences.saveTemporalSignUpStepsName(name)
    }
}