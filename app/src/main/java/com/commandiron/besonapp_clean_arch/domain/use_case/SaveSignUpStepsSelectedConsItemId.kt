package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences

class SaveSignUpStepsSelectedConsItemId(
    private val preferences: Preferences
) {
    operator fun invoke(mainConstructionItemId: Int){
        preferences.saveTemporalSignUpStepsSelectedConsItemId(mainConstructionItemId)
    }
}