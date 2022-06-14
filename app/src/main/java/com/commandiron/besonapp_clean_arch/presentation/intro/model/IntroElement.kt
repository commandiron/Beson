package com.commandiron.besonapp_clean_arch.presentation.intro.model

import com.commandiron.besonapp_clean_arch.R

data class IntroElement(
    val imageResource: Int = 0,
    val explanationText: String = ""
)

val defaultIntroElements = listOf(
    IntroElement(
        imageResource = R.drawable.beson_splash,
        explanationText = "BEŞON, TÜRKİYE İNŞAAT MALZEME VE İŞÇİLİK FİYATLARI TAKİP VE TAŞERON BULMA PLATFORMUDUR."
    ),
    IntroElement(
        imageResource = R.drawable.beson_signup,
        explanationText = "İSTER FİRMA OLARAK İSTER MÜŞTERİ OLARAK KAYIT OLUN."
    ),
    IntroElement(
        imageResource = R.drawable.beson_post_price,
        explanationText = "FİRMA OLARAK FİYAT GÖNDEREBİLİR, MÜŞTERİ BULABİLİRSİNİZ."
    ),
    IntroElement(
        imageResource = R.drawable.beson_price_list,
        explanationText = " ya da MÜŞTERİ OLARAK İSTEDİĞİNİZ FİYAT KATEGORİSİNİ GÖREBİLİR VE FİRMA BULABİLİRSİNİZ."
    )
)