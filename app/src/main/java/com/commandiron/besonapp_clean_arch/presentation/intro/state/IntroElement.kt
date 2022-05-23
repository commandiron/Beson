package com.commandiron.besonapp_clean_arch.presentation.intro.state

import com.commandiron.besonapp_clean_arch.R

data class IntroElement(
    val imageResource: Int = 0,
    val explanationText: String = ""
)

val defaultIntroElements = listOf(
    IntroElement(
        imageResource = R.drawable.besonapp_splash,
        explanationText = "BEŞON, BİNA YAPIM FİYATLARI TAKİP VE TAŞERON BULMA PLATFORMUDUR."
    ),
    IntroElement(
        imageResource = R.drawable.besonapp_splash,
        explanationText = "BEŞON, BİNA YAPIM FİYATLARI TAKİP VE TAŞERON BULMA PLATFORMUDUR."
    ),
    IntroElement(
        imageResource = R.drawable.besonapp_splash,
        explanationText = "BEŞON, BİNA YAPIM FİYATLARI TAKİP VE TAŞERON BULMA PLATFORMUDUR."
    )
)