package com.jangjh123.allpouse_android.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

@Composable
fun background() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF191919),
        lightModeColor = Color(0xFFFFFFFF)
    )

@Composable
fun subBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF1D1D1D),
        lightModeColor = Color(0xFFF8F8F8)
    )

@Composable
fun contentBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF232323),
        lightModeColor = Color(0xFFF2F2F2)
    )

@Composable
fun mainTextColor() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFFEFEFEF),
        lightModeColor = Color(0xFF010101)
    )

@Composable
fun subTextColor() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFFAEAEAE),
        lightModeColor = Color(0xFF848484)
    )

@Composable
fun mainGradient0() = Color(0xFFB74C4C)

@Composable
fun mainGradient1() = Color(0xFFC16666)

@Composable
fun mainColor() = Color(0xFFAD3232)

@Composable
fun darkFilter() = Color(0x88000000)

@Composable
fun brandLogoBackground() = Color(0x09000000)

@Composable
fun onDarkModeOrNot(darkModeColor: Color, lightModeColor: Color): Color {
    return if (isSystemInDarkTheme()) {
        darkModeColor
    } else {
        lightModeColor
    }
}