package com.jangjh123.allpouse_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.ui.component.ComposableFunctions.GradientButton
import com.jangjh123.allpouse_android.ui.theme.*

sealed class FontType {
    object Light : FontType()
    object Medium : FontType()
    object Bold : FontType()
}

object ComposableFunctions {
    @Composable
    fun APText(
        modifier: Modifier? = Modifier,
        text: String,
        fontSize: TextUnit? = null,
        fontType: FontType? = null,
        textAlign: TextAlign? = null,
        letterSpacing: TextUnit? = null,
        fontColor: Color? = null
    ) {
        Text(
            modifier = modifier.let {
                modifier?.wrapContentSize()
            } ?: Modifier.wrapContentSize(),
            text = text,
            fontSize = fontSize.let {
                fontSize
            } ?: 14.sp,
            fontFamily =
            when (fontType) {
                FontType.Light -> {
                    notoSansLight
                }
                FontType.Medium -> {
                    notoSansMedium
                }
                FontType.Bold -> {
                    notoSansBold
                }
                null -> {
                    notoSansMedium
                }
            },
            style = TextStyle(
                platformStyle = PlatformTextStyle(includeFontPadding = false)
            ),
            textAlign = textAlign.let {
                textAlign
            } ?: TextAlign.Start,
            letterSpacing = letterSpacing.let {
                letterSpacing
            } ?: (-0.5).sp,
            color = fontColor.let {
                fontColor
            } ?: mainTextColor()
        )
    }

    @Composable
    fun GradientButton(
        modifier: Modifier,
        text: String,
        fontSize: TextUnit? = 14.sp
    ) {
        Box(
            modifier = modifier
                .clip(shape = RoundedCornerShape(12.dp))
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            mainColor(),
                            mainGradient0(),
                            mainGradient1()
                        )
                    )
                )
        ) {
            APText(
                modifier = Modifier.align(Alignment.Center),
                text = text,
                fontSize = fontSize,
                fontColor = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    GradientButton(
        modifier = Modifier
            .width(300.dp)
            .height(60.dp),
        text = "example"
    )
}