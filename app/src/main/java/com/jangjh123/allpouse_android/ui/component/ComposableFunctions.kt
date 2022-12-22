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
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.screen.main.board.DummyPost
import com.jangjh123.allpouse_android.ui.screen.main.board.dummyPosts
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
fun GradientIconButton(
    modifier: Modifier,
    icon: Painter,
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
        Row(
            modifier = Modifier
                .align(Center)
        ) {
            Icon(
                modifier = Modifier
                    .align(CenterVertically)
                    .size(16.dp),
                painter = icon,
                contentDescription = "buttonIcon",
                tint = Color.White
            )
            APText(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .align(CenterVertically),
                text = text,
                fontSize = fontSize,
                fontColor = Color.White
            )
        }
    }
}

@Composable
fun APTextField(
    modifier: Modifier,
    textFieldState: MutableState<String>,
    onValueChanged: (String) -> Unit,
    focusManager: FocusManager,
    keyboardOptions: KeyboardOptions? = null,
    singleLine: Boolean? = null
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
            singleLine = singleLine.let {
                singleLine
            } ?: true,
            colors = textFieldColors()
        )
    }
}

@Composable
fun RoundedCornerButton(
    modifier: Modifier,
    text: String,
    backgroundColor: Color? = null,
    fontColor: Color? = null,
    onClickButton: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(30.dp)
            )
            .background(
                color = backgroundColor.let {
                    backgroundColor
                } ?: contentBackground()
            )
            .clickable {
                onClickButton()
            }
    ) {
        APText(
            modifier = Modifier
                .align(Center),
            text = text,
            fontColor = fontColor.let {
                fontColor
            } ?: mainTextColor(),
            fontSize = 14.sp
        )
    }
}

@Composable
fun RoundedCornerIconButton(
    modifier: Modifier,
    text: String,
    backgroundColor: Color? = null,
    fontColor: Color? = null,
    icon: Painter,
    iconTint: Color? = null,
    onClickButton: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(30.dp))
            .background(
                color = backgroundColor ?: contentBackground()
            )
            .clickable {
                onClickButton()
            }
    ) {
        Row(
            modifier = Modifier
                .align(Center)
        ) {
            Icon(
                modifier = Modifier
                    .size(16.dp)
                    .align(CenterVertically),
                painter = icon,
                contentDescription = "buttonIcon",
                tint = iconTint ?: subTextColor()
            )

            APText(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(CenterVertically),
                text = text,
                fontColor = fontColor ?: mainTextColor(),
                fontSize = 14.sp
            )
        }
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
fun Perfume(
    modifier: Modifier,
    perfumeName: String,
    brandName: String,
    image: Painter,
    keywordCount: Int
) {
    Box(
        modifier = modifier
            .wrapContentHeight()
            .width(160.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color = subBackground())
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier
                    .padding(
                        top = 8.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(160.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(contentBackground())
                    .padding(10.dp),
                painter = image,
                contentDescription = "perfumeImage",
                contentScale = ContentScale.FillBounds
            )
            APText(
                modifier = Modifier.padding(
                    top = 4.dp,
                    start = 10.dp,
                    end = 10.dp
                ),
                text = perfumeName,
                fontColor = mainTextColor(),
                fontSize = 14.sp
            )
            APText(
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                text = brandName,
                fontColor = subTextColor(),
                fontSize = 12.sp
            )
            APAppendedText(modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 8.dp),
                annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = subTextColor(),
                            fontSize = 12.sp
                        )
                    ) {
                        append("5개 중")
                        append(" ")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = mainColor(),
                            fontSize = 12.sp
                        )
                    ) {
                        append("$keywordCount")
                    }
                    withStyle(
                        style = SpanStyle(
                            color = subTextColor(),
                            fontSize = 12.sp
                        )
                    ) {
                        append(" ")
                        append("개")
                        append(" ")
                        append("일치")
                    }
                })
        }
    }
}

@Composable
fun Review(
    modifier: Modifier,
    score: Float,
    perfumeName: String,
    image: Painter,
    title: String,
    body: String,
    author: String, // User
    authorImage: Painter,
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
                        .padding(end = 4.dp)
                        .wrapContentSize()
                        .align(CenterEnd)
                ) {
                    Image(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(shape = CircleShape)
                            .size(24.dp),
                        painter = authorImage,
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
                        .fillMaxWidth()
                        .background(color = contentBackground()),
                    painter = image,
                    contentDescription = "reviewImage",
                    contentScale = ContentScale.FillBounds
                )

                Box(
                    Modifier
                        .padding(4.dp)
                        .height(100.dp)
                        .fillMaxWidth()
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
                            contentDescription = "hitIcon",
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

sealed class DummyComment {
    data class DummyReviewComment(
        val userName: String,
        val date: String,
        val userProfileImage: Int,
        val body: String,
        val commentsInComment: ArrayList<DummyCommentInReviewComment>
    ) : DummyComment()

    data class DummyCommentInReviewComment(
        val userName: String,
        val date: String,
        val userProfileImage: Int,
        val body: String
    ) : DummyComment()

    data class DummyPostComment(
        val authorName: String,
        val authorImage: Int,
        val date: String,
        val body: String
    ) : DummyComment()
}


@Composable
fun Comment(modifier: Modifier, comment: DummyComment) {
    when (comment) {
        is DummyComment.DummyReviewComment -> {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .size(48.dp),
                        painter = painterResource(
                            id = comment.userProfileImage
                        ),
                        contentDescription = "commentWriterProfileImage",
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp
                            )
                    ) {
                        Row {
                            APText(
                                modifier = Modifier
                                    .align(CenterVertically),
                                text = comment.userName,
                                fontSize = 12.sp
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 8.dp
                                    )
                                    .align(CenterVertically),
                                text = comment.date,
                                fontSize = 10.sp,
                                fontColor = subTextColor()
                            )
                        }

                        APText(
                            text = comment.body
                        )

                        APText(
                            modifier = Modifier
                                .clickableWithoutRipple {
                                    // todo : commentInComment
                                },
                            text = stringResource(
                                id = R.string.write_comment_in_comment
                            ),
                            fontSize = 12.sp,
                            fontColor = subTextColor()
                        )
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                if (comment.commentsInComment.isNotEmpty()) {
                    comment.commentsInComment.forEach {
                        Comment(
                            modifier = Modifier
                                .padding(
                                    start = 60.dp
                                ),
                            comment = it
                        )
                    }
                }
            }
        }
        is DummyComment.DummyCommentInReviewComment -> {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 8.dp
                        )
                ) {
                    Image(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .size(48.dp),
                        painter = painterResource(
                            id = comment.userProfileImage
                        ),
                        contentDescription = "commentWriterProfileImage",
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp
                            )
                    ) {
                        Row {
                            APText(
                                modifier = Modifier
                                    .align(CenterVertically),
                                text = comment.userName,
                                fontSize = 12.sp
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 8.dp
                                    )
                                    .align(CenterVertically),
                                text = comment.date,
                                fontSize = 10.sp,
                                fontColor = subTextColor()
                            )
                        }

                        APText(
                            text = comment.body
                        )
                    }
                }
            }
        }
        is DummyComment.DummyPostComment -> {
            Column {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Image(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .size(48.dp),
                        painter = painterResource(
                            id = comment.authorImage
                        ),
                        contentDescription = "commentWriterProfileImage",
                        contentScale = ContentScale.FillBounds
                    )

                    Column(
                        modifier = Modifier
                            .padding(
                                horizontal = 8.dp
                            )
                            .align(CenterVertically)
                    ) {
                        Row {
                            APText(
                                modifier = Modifier
                                    .align(CenterVertically),
                                text = comment.authorName,
                                fontSize = 12.sp
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        horizontal = 8.dp
                                    )
                                    .align(CenterVertically),
                                text = comment.date,
                                fontSize = 10.sp,
                                fontColor = subTextColor()
                            )
                        }

                        APText(
                            text = comment.body
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Keyword(modifier: Modifier, keyword: String) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .width(100.dp)
            .height(36.dp)
            .background(contentBackground())
    ) {
        APText(
            modifier = Modifier.align(Center),
            text = keyword,
            fontColor = mainTextColor(),
            fontSize = 14.sp
        )
    }
}

@Composable
fun Brand(
    modifier: Modifier,
    brandName: String,
    brandImage: Painter,
    brandPerfumeCount: Int,
    brandHit: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .height(100.dp)
            .background(
                color = subBackground()
            )
            .clickable {
                onClick()
            }
    ) {
        Box(
            modifier = Modifier
                .padding(12.dp)
                .weight(0.5f)
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .background(
                    color = Color.White
                )
        ) {
            Image(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(12.dp)
                    .align(Center),
                painter = brandImage,
                contentDescription = "brandLogoImage",
                contentScale = ContentScale.Inside
            )
        }

        Column(
            modifier = Modifier
                .padding(12.dp)
                .weight(0.5f)
                .align(CenterVertically)
        ) {
            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = brandName,
                fontType = FontType.Bold,
                fontSize = 20.sp,
                lines = 1
            )

            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = stringResource(
                    id = R.string.brand_perfume_count, brandPerfumeCount
                ),
                fontSize = 12.sp,
                fontColor = subTextColor()
            )

            Row(
                modifier = Modifier
                    .align(CenterHorizontally)
            ) {
                Icon(
                    modifier = Modifier
                        .size(12.dp)
                        .align(CenterVertically),
                    painter = painterResource(
                        id = R.drawable.ic_viewed
                    ),
                    contentDescription = "hitIcon",
                    tint = subTextColor()
                )

                APText(
                    modifier = Modifier
                        .padding(
                            start = 4.dp
                        )
                        .align(CenterVertically),
                    text = "$brandHit",
                    fontSize = 12.sp,
                    fontColor = subTextColor()
                )
            }

        }
    }
}

@Composable
fun PostWithBoardName(
    modifier: Modifier,
    board: String,
    postName: String,
    like: Int,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .background(
                color = subBackground()
            )
    ) {

        Row(Modifier.fillMaxHeight()) {
            APText(
                modifier = Modifier
                    .padding(
                        start = 12.dp
                    )
                    .padding(
                        vertical = 8.dp
                    ),
                text = board,
                fontSize = 10.sp,
                fontColor = mainColor()
            )

            APText(
                modifier = Modifier
                    .padding(
                        start = 8.dp
                    )
                    .align(CenterVertically),
                text = postName,
                fontSize = 12.sp,
                lines = 1
            )
        }

        Row(
            Modifier
                .fillMaxHeight()
                .align(CenterEnd)
        ) {
            Icon(
                modifier = Modifier
                    .padding(2.dp)
                    .align(CenterVertically)
                    .size(12.dp),
                painter = painterResource(
                    id = R.drawable.ic_filled_heart
                ),
                contentDescription = "postLikeIcon",
                tint = subTextColor()
            )

            APText(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(
                        end = 4.dp
                    ),
                text = "$like",
                fontColor = subTextColor(),
                fontSize = 10.sp
            )

            Icon(
                modifier = Modifier
                    .padding(2.dp)
                    .align(CenterVertically)
                    .size(12.dp),
                painter = painterResource(
                    id = R.drawable.ic_comment
                ),
                contentDescription = "postCommentIcon",
                tint = subTextColor()
            )

            APText(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(
                        end = 12.dp
                    ),
                text = "$like",
                fontColor = subTextColor(),
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun Post(modifier: Modifier, post: DummyPost) {
    Column(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .fillMaxWidth()
            .background(
                color = subBackground()
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(0.6f)
                    .align(CenterVertically)
            ) {
                APText(
                    modifier = Modifier
                        .align(CenterVertically),
                    text = post.title,
                    lines = 1
                )
            }

            Row(
                modifier = Modifier
                    .padding(12.dp)
                    .weight(0.4f),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .size(24.dp)
                        .align(CenterVertically),
                    painter = painterResource(
                        id = post.authorImage
                    ),
                    contentDescription = "postAuthorImage",
                    contentScale = ContentScale.FillBounds
                )

                APText(
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        )
                        .align(CenterVertically),
                    text = post.authorName,
                    fontSize = 12.sp,
                    lines = 1
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (post.imageList != null) {
                Image(
                    modifier = Modifier
                        .padding(
                            vertical = 12.dp
                        )
                        .padding(
                            start = 12.dp
                        )
                        .clip(
                            shape = RoundedCornerShape(12.dp)
                        )
                        .size(80.dp),
                    painter = painterResource(
                        id = post.imageList[0]
                    ),
                    contentDescription = "postThumbnail",
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(80.dp)
            ) {
                APText(
                    text = post.body,
                    lines = 3,
                    fontSize = 12.sp,
                    fontColor = subTextColor()
                )

                Row(
                    modifier = Modifier
                        .align(BottomEnd)
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(CenterVertically)
                            .size(12.dp),
                        painter = painterResource(
                            id = R.drawable.ic_viewed
                        ),
                        contentDescription = "postViewedIcon",
                        tint = subTextColor()
                    )

                    APText(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(
                                end = 4.dp
                            ),
                        text = "${post.hit}",
                        fontColor = subTextColor(),
                        fontSize = 10.sp
                    )

                    Icon(
                        modifier = Modifier
                            .padding(2.dp)
                            .align(CenterVertically)
                            .size(12.dp),
                        painter = painterResource(
                            id = R.drawable.ic_comment
                        ),
                        contentDescription = "postCommentIcon",
                        tint = subTextColor()
                    )

                    APText(
                        modifier = Modifier
                            .align(CenterVertically)
                            .padding(
                                end = 12.dp
                            ),
                        text = "${post.commentCount}",
                        fontColor = subTextColor(),
                        fontSize = 10.sp
                    )
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
        painter = painterResource(
            id = R.drawable.ic_arrow_back
        ),
        contentDescription = "goBack",
        tint = mainTextColor()
    )
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    Post(
        modifier = Modifier,
        post = dummyPosts[0]
    )
}
