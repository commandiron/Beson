package com.commandiron.besonapp_clean_arch.domain.use_case

import com.commandiron.besonapp_clean_arch.domain.preferences.Preferences
import com.commandiron.besonapp_clean_arch.presentation.model.MainConstructionItem
import com.commandiron.besonapp_clean_arch.presentation.model.defaultConstructionItems

class LoadSignUpStepsSelectedConsItem(
    private val preferences: Preferences
) {
    operator fun invoke(): MainConstructionItem {
        val itemId = preferences.loadTemporalSignUpStepsSelectedConsItemId()
        return defaultConstructionItems[itemId]
    }
}