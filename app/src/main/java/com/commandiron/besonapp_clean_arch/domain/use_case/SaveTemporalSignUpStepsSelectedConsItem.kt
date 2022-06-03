package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.domain.model.MainConstructionItem

class SaveTemporalSignUpStepsSelectedConsItem(
    private val preferences: Preferences
) {
    operator fun invoke(mainConstructionItem: MainConstructionItem){
        preferences.saveTemporalSignUpStepsSelectedConsItemId(mainConstructionItem.id)
    }
}