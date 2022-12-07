package com.jangjh123.allpouse_android.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.theme.*

sealed class FontType {
    object Light : FontType()
    object Medium : FontType()
    object Bold : FontType()
}

@Composable
fun APText(
    modifier: Modifier? = Modifier,
    text: String,
    fontSize: TextUnit? = null,
    fontType: FontType? = null,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit? = null,
    fontColor: Color? = null,
    lines: Int? = null,
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
        } ?: mainTextColor(),
        maxLines = lines.let {
            lines
        } ?: Int.MAX_VALUE,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun APAppendedText(
    modifier: Modifier? = Modifier,
    annotatedString: AnnotatedString,
    fontSize: TextUnit? = null,
    fontType: FontType? = null,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit? = null,
    fontColor: Color? = null,
) {
    Text(
        modifier = modifier.let {
            modifier?.wrapContentSize()
        } ?: Modifier.wrapContentSize(),
        text = annotatedString,
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
        } ?: mainTextColor(),
    )
}


@Composable
fun GradientButton(
    modifier: Modifier,
    text: String,
    fontSize: TextUnit? = 14.sp,
    onClickButton: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .clickable {
                onClickButton()
            }
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

@Composable
fun APTextField(
    modifier: Modifier,
    textFieldState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions? = null,
) {
    CompositionLocalProvider(LocalTextSelectionColors.provides(textSelectionColor())) {
        TextField(
            modifier = modifier,
            value = textFieldState.value,
            onValueChange = { onValueChanged(it) },
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = keyboardOptions.let {
                keyboardOptions
            } ?: KeyboardOptions.Default,
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            colors = textFieldColors()
        )
    }
}

@Composable
fun textSelectionColor() = TextSelectionColors(
    handleColor = mainColor(),
    backgroundColor = mainColor()
)

@Composable
fun textFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = mainTextColor(),
    backgroundColor = subBackground(),
    cursorColor = mainTextColor(),
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
)

@Composable
fun CloseIcon(modifier: Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.close),
        contentDescription = "close",
        tint = mainTextColor()
    )
}

@Composable
fun ReviewListItem(
    modifier: Modifier,
    score: Float,
    perfumeName: String,
    image: Int,
    title: String,
    body: String,
    author: String, // User
    authorImage: Int,
    hit: Int,
    recommend: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .background(color = subBackground())
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .padding(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(4.dp)
            ) {
                APText(
                    modifier = Modifier
                        .padding(4.dp)
                        .align(CenterStart),
                    text = title,
                    fontSize = 14.sp,
                    lines = 1,
                    fontType = FontType.Bold
                )

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(CenterEnd)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(shape = CircleShape)
                            .size(24.dp),
                        painter = painterResource(id = authorImage),
                        contentDescription = "reviewAuthorImage",
                        contentScale = ContentScale.FillBounds
                    )

                    APText(
                        modifier = Modifier
                            .align(CenterVertically),
                        text = author,
                        fontSize = 12.sp
                    )
                }
            }

            Row {
                Image(
                    modifier = Modifier
                        .padding(4.dp)
                        .clip(shape = RoundedCornerShape(12.dp))
                        .size(100.dp)
                        .background(color = contentBackground()),
                    painter = painterResource(id = image),
                    contentDescription = "reviewImage",
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    Modifier
                        .padding(4.dp)
                        .height(100.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        APText(
                            text = perfumeName,
                            fontSize = 14.sp
                        )
                        APText(
                            text = body,
                            fontSize = 11.sp,
                            lines = 2,
                            fontColor = subTextColor()
                        )
                    }

                    APText(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .align(Alignment.BottomStart),
                        text = "$score", fontSize = 18.sp,
                        fontColor = mainColor(),
                        fontType = FontType.Bold
                    )

                    Row(modifier = Modifier.align(Alignment.BottomEnd)) {
                        Icon(
                            modifier = Modifier
                                .size(10.dp)
                                .align(CenterVertically),
                            painter = painterResource(id = R.drawable.ic_viewed),
                            contentDescription = "viewedIcon",
                            tint = subTextColor()
                        )

                        APText(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .align(CenterVertically),
                            text = "$hit",
                            fontSize = 10.sp,
                            fontColor = subTextColor()
                        )

                        Icon(
                            modifier = Modifier
                                .size(10.dp)
                                .align(CenterVertically),
                            painter = painterResource(id = R.drawable.ic_filled_heart),
                            contentDescription = "viewedIcon",
                            tint = subTextColor()
                        )

                        APText(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .align(CenterVertically),
                            text = "$recommend",
                            fontSize = 10.sp,
                            fontColor = subTextColor()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BackButton(modifier: Modifier, onClickBackButton: () -> Unit) {
    Icon(
        modifier = modifier
            .clickableWithoutRipple {
                onClickBackButton()
            },
        painter = painterResource(id = R.drawable.ic_arrow_back),
        contentDescription = "goBack",
        tint = mainTextColor()
    )
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
}
