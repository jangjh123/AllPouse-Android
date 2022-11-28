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
        darkModeColor = Color(0xFF170F0D),
        lightModeColor = Color(0xFFFFFFFF)
    )

@Composable
fun subBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF281510),
        lightModeColor = Color(0xFFEDD9D3)
    )

@Composable
fun contentBackground() =
    onDarkModeOrNot(
        darkModeColor = Color(0xFF361D16),
        lightModeColor = Color(0xFFE8CDC5)
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
fun mainGradient0() = Color(0xFFA26250)

@Composable
fun mainGradient1() = Color(0xFFAC7363)

@Composable
fun mainColor() = Color(0xFF98513D)

@Composable
fun onDarkModeOrNot(darkModeColor: Color, lightModeColor: Color): Color {
    return if (isSystemInDarkTheme()) {
        darkModeColor
    } else {
        lightModeColor
    }
}