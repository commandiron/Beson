package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class SaveTemporalSignUpStepsPhoneNumber(
    private val preferences: Preferences
) {
    operator fun invoke(phoneNumber: String){
        preferences.saveTemporalSignUpStepsPhoneNumber(phoneNumber)
    }
}