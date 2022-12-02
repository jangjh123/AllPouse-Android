package com.jangjh123.allpouse_android.ui.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
    Image(
        modifier = modifier,
        painter = painterResource(id = R.drawable.close),
        contentDescription = "close",
        contentScale = ContentScale.FillBounds,
        colorFilter = ColorFilter.tint(color = mainTextColor())
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
    Box(modifier = modifier
        .fillMaxWidth()
        .height(108.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(color = subBackground())
    ) {
        Column(Modifier
            .wrapContentSize()
            .padding(4.dp)) {
            Row {
                Image(modifier = Modifier
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .size(100.dp)
                    .background(color = contentBackground()),
                    painter = painterResource(id = image),
                    contentDescription = "reviewImage",
                    contentScale = ContentScale.FillBounds)

                Column(modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = 8.dp, vertical = 4.dp)) {
                    APText(
                        text = perfumeName,
                        fontSize = 14.sp,
                        fontType = FontType.Bold
                    )
                    APText(
                        modifier = Modifier.padding(top = 4.dp),
                        text = title,
                        fontSize = 12.sp,
                        lines = 1)
                    APText(
                        text = body,
                        fontSize = 11.sp,
                        lines = 2,
                        fontColor = subTextColor())
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverScrollDisabledScope(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ReviewListItem(modifier = Modifier,
        score = 4.3f,
        perfumeName = "TestPerfume",
        image = R.drawable.ad_banner_0,
        title = "Example Title",
        body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
        author = "Example Author",
        authorImage = R.drawable.ad_banner_1,
        hit = 3132,
        recommend = 47)
}