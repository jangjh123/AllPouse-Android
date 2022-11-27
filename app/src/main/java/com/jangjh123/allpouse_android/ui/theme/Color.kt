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
        darkModeColor = Color(0xFF372727),
        lightModeColor = Color(0xFFFFFFFF)
    )

@Composable
fun subBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF473D3C),
        lightModeColor = Color(0xFFFAF9F9)
    )

@Composable
fun contentBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF7A6866),
        lightModeColor = Color(0xFFF0EDEC)
    )

@Composable
fun mainTextColor() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFFE5E5E5),
        lightModeColor = Color(0xFF494949)
    )

@Composable
fun subTextColor() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFFAEAEAE),
        lightModeColor = Color(0xFF848484)
    )

@Composable
fun mainGradient0() = Color(0xFFA75A52)

@Composable
fun mainGradient1() = Color(0xFFBA7972)

@Composable
fun mainColor() = Color(0xFF965149)

@Composable
fun onDarkModeOrNot(darkModeColor: Color, lightModeColor: Color): Color {
    return if (isSystemInDarkTheme()) {
        darkModeColor
    } else {
        lightModeColor
    }
}