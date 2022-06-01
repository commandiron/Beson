package com.commandiron.besonapp_clean_arch.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.commandiron.besonapp_clean_arch.R

val Roboto = FontFamily(
    Font(R.font.roboto_black, weight = FontWeight.Black, style = FontStyle.Normal),
    Font(R.font.roboto_blackitalic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.roboto_bold, weight = FontWeight.Bold, style = FontStyle.Normal),
    Font(R.font.roboto_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.roboto_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.roboto_light, weight = FontWeight.Light, style = FontStyle.Normal),
    Font(R.font.roboto_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.roboto_medium, weight = FontWeight.Medium, style = FontStyle.Normal),
    Font(R.font.roboto_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.roboto_regular, weight = FontWeight.Normal, style = FontStyle.Normal),
    Font(R.font.roboto_thin, weight = FontWeight.Thin, style = FontStyle.Normal),
    Font(R.font.roboto_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic),
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 30.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Roboto,
        fontWeight = FontWeight.Medium,
        fontSize = 9.sp
    )
)