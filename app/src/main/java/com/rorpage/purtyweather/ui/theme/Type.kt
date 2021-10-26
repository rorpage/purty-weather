package com.rorpage.purtyweather.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rorpage.purtyweather.R

var nunitoFamily = FontFamily(
    Font(R.font.nunito_black, FontWeight.Black),
    Font(R.font.nunito_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.nunito_bold, FontWeight.Bold),
    Font(R.font.nunito_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.nunito_extrabold, FontWeight.ExtraBold),
    Font(R.font.nunito_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.nunito_extralight, FontWeight.ExtraLight),
    Font(R.font.nunito_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.nunito_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.nunito_light, FontWeight.Light),
    Font(R.font.nunito_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.nunito_regular, FontWeight.Normal),
    Font(R.font.nunito_semibold, FontWeight.SemiBold),
    Font(R.font.nunito_semibolditalic, FontWeight.SemiBold, FontStyle.Italic)
)

val Typography = Typography(
    defaultFontFamily = nunitoFamily,
    h1 = TextStyle(
        fontSize = 100.sp,
        fontWeight = FontWeight.Bold
    ),
    h2 = TextStyle(
        fontSize = 36.sp,
        fontWeight = FontWeight.Bold
    )
)
