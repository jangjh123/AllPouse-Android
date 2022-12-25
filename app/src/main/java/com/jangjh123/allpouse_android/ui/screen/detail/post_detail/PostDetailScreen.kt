package com.jangjh123.allpouse_android.ui.screen.detail.post_detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterStart
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.main.board.DummyPost
import com.jangjh123.allpouse_android.ui.screen.main.board.dummyPosts
import com.jangjh123.allpouse_android.ui.theme.mainColor
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PostDetailScreen(
    post: DummyPost
) {
    val commentTextFieldState = remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        state = scrollState
    ) {
        item {
            APText(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                    )
                    .padding(
                        top = 24.dp
                    ),
                text = post.title,
                fontType = FontType.Bold,
                fontSize = 20.sp,
                lines = 1
            )

            Box(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
                    .padding(
                        top = 8.dp
                    )
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .align(CenterStart)
                ) {
                    Image(
                        modifier = Modifier
                            .clip(
                                shape = CircleShape
                            )
                            .size(36.dp)
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
                        fontColor = subTextColor()
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(
                            end = 12.dp
                        )
                        .align(CenterEnd)
                ) {
                    APText(
                        text = "2022년 12월 19일 21:09",
                        fontColor = subTextColor(),
                        fontSize = 12.sp
                    )
                    Row(
                        modifier = Modifier
                            .padding(
                                top = 4.dp
                            )
                            .align(End)
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
                            text = "632",
                            fontSize = 12.sp,
                            fontColor = subTextColor()
                        )

                        Icon(
                            modifier = Modifier
                                .padding(
                                    start = 8.dp
                                )
                                .size(12.dp)
                                .align(CenterVertically),
                            painter = painterResource(
                                id = R.drawable.ic_comment
                            ),
                            contentDescription = "recommendedIcon",
                            tint = subTextColor()
                        )

                        APText(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp
                                )
                                .align(CenterVertically),
                            text = "8",
                            fontSize = 12.sp,
                            fontColor = subTextColor()
                        )
                    }

                }
            }

            Divider(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = subBackground()
            )

            val pagerState = rememberPagerState()

            if (post.imageList != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 12.dp
                        )
                        .padding(
                            bottom = 12.dp
                        )
                ) {
                    HorizontalPager(
                        count = post.imageList.size,
                        state = pagerState
                    ) { index ->
                        Image(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp
                                )
                                .clip(
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .fillMaxWidth()
                                .height(300.dp)
                                .background(
                                    color = subBackground()
                                ),
                            painter = painterResource(
                                id = post.imageList[index]
                            ),
                            contentDescription = "postImage",
                            contentScale = ContentScale.Inside
                        )
                    }
                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp
                            )
                            .align(CenterHorizontally),
                        pagerState = pagerState,
                        activeColor = mainColor(),
                        inactiveColor = Color.LightGray
                    )
                }


            }

            Box(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .heightIn(
                        min = 200.dp
                    )
            ) {
                APText(
                    text = post.body
                )
            }

            Divider(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 24.dp
                    )
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = subBackground()
            )

            Row(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
            ) {
                APText(
                    modifier = Modifier
                        .align(CenterVertically),
                    text = stringResource(
                        id = R.string.comment
                    ),
                    fontSize = 20.sp,
                    fontType = FontType.Bold
                )

                APText(
                    modifier = Modifier
                        .padding(
                            start = 8.dp
                        )
                        .align(CenterVertically),
                    text = "${post.commentCount}개",
                    fontColor = subTextColor()
                )
            }
        }

        item {
            Spacer(
                modifier = Modifier
                    .height(12.dp)
            )
        }

        items(dummyPostComment) { comment ->
            Comment(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 12.dp
                    ),
                comment = comment
            )

            Divider(
                modifier = Modifier
                    .padding(
                        horizontal = 12.dp
                    )
                    .fillMaxWidth(),
                thickness = 1.dp,
                color = subBackground()
            )
        }

        item {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                APText(
                    text = stringResource(
                        id = R.string.write_comment
                    ),
                    fontSize = 18.sp,
                    fontType = FontType.Bold
                )

                APTextField(
                    modifier = Modifier
                        .padding(
                            top = 8.dp
                        )
                        .fillMaxWidth(),
                    textFieldState = commentTextFieldState,
                    onValueChanged = { commentTextFieldState.value = it },
                    focusManager = LocalFocusManager.current,
                    singleLine = false
                )

                val buttonAlphaState = animateFloatAsState(
                    targetValue =
                    if (commentTextFieldState.value.isNotEmpty()) 1.0f
                    else 0f
                )

                GradientButton(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        )
                        .fillMaxWidth()
                        .height(50.dp)
                        .alpha(buttonAlphaState.value),
                    text = stringResource(
                        id = R.string.post_comment
                    )
                ) {

                }
                LaunchedEffect(commentTextFieldState.value) {
                    if (commentTextFieldState.value.isNotEmpty()) {
                        scrollState.animateScrollBy(200f)
                    }
                }
            }
        }
    }
}

val dummyPostComment = listOf(
    DummyComment.DummyPostComment(
        "테스터",
        R.drawable.ad_banner_2,
        "2022년 12월 22일 12:29",
        "테스트 댓글입니다."
    ),
    DummyComment.DummyPostComment(
        "테스터",
        R.drawable.ad_banner_2,
        "2022년 12월 22일 12:29",
        "테스트 댓글입니다."
    ),
    DummyComment.DummyPostComment(
        "테스터",
        R.drawable.ad_banner_2,
        "2022년 12월 22일 12:29",
        "테스트 댓글입니다."
    ),
    DummyComment.DummyPostComment(
        "테스터",
        R.drawable.ad_banner_2,
        "2022년 12월 22일 12:29",
        "테스트 댓글입니다."
    ),
    DummyComment.DummyPostComment(
        "테스터",
        R.drawable.ad_banner_2,
        "2022년 12월 22일 12:29",
        "테스트 댓글입니다."
    ),
)

@Preview(showBackground = true)
@Composable
private fun Preview() {
    PostDetailScreen(dummyPosts[0])
}