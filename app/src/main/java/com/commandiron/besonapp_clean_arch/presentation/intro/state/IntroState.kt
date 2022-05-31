package com.commandiron.besonapp_clean_arch.presentation.intro.state

import androidx.compose.ui.graphics.Color
import com.commandiron.besonapp_clean_arch.presentation.intro.model.IntroElement
import com.commandiron.besonapp_clean_arch.presentation.intro.model.defaultIntroElements
import com.commandiron.besonapp_clean_arch.ui.theme.PrimaryColor

data class IntroState(
    val introElements: List<IntroElement> = defaultIntroElements,
    val footerColor : Color= PrimaryColor,
    val bottomBarText: String = "",
)