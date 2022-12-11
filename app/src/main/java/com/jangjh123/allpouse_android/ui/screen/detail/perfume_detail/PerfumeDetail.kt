package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.ContentState.InformationContent
import com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.ContentState.ReviewContent
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_HEIGHT_DP
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.launch

sealed class ContentState {
    object InformationContent : ContentState()
    object ReviewContent : ContentState()
}

@Composable
fun PerfumeDetail() {
    val scrollState = rememberScrollState()
    val nameSpaceHeightState = remember { mutableStateOf(0) }
    val contentState = remember { mutableStateOf<ContentState>(InformationContent) }
    var newNameSpaceShowingOffset = 0
    with(LocalDensity.current) {
        newNameSpaceShowingOffset += nameSpaceHeightState.value + (SCREEN_HEIGHT_DP * 0.3f).roundToPx()
    }
    val newNameSpaceAlphaState =
        animateFloatAsState(targetValue = if (scrollState.value >= newNameSpaceShowingOffset) 1f else 0f)
    val scope = rememberCoroutineScope()

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState
                )
                .background(
                    color = subBackground()
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(SCREEN_HEIGHT_DP * 0.3f)
                    .background(
                        color = contentBackground()
                    )
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    painter = painterResource(
                        id = R.drawable.perfume_test_0
                    ),
                    contentDescription = "perfumeImage"
                )
            }

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .onGloballyPositioned { coordinates ->
                        nameSpaceHeightState.value = coordinates.size.height
                    }
            ) {
                Spacer(modifier = Modifier.height(12.dp))
                APText(
                    text = "테스트 향수 1996 Reissue",
                    fontSize = 24.sp,
                    fontType = FontType.Bold
                )
                APText(
                    text = "테스트 브랜드",
                    fontSize = 12.sp,
                    fontColor = subTextColor()
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            Column(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 36.dp,
                            topEnd = 36.dp
                        )
                    )
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = background())
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(CenterHorizontally)
                        .padding(24.dp)
                ) {
                    ContentButton(
                        modifier = Modifier
                            .weight(0.5f),
                        content = stringResource(
                            id = R.string.perfume_information
                        ),
                        contentState = contentState
                    ) {
                        contentState.value = InformationContent
                    }

                    Spacer(
                        modifier = Modifier
                            .width(12.dp)
                    )

                    ContentButton(
                        modifier = Modifier
                            .weight(0.5f),
                        content = stringResource(
                            id = R.string.perfume_review
                        ),
                        contentState = contentState
                    ) {
                        contentState.value = ReviewContent
                    }
                }

                when (contentState.value) {
                    InformationContent -> {
                        PerfumeDetailInformationContent()
                    }
                    ReviewContent -> {
                        PerfumeDetailReviewsContent()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .alpha(newNameSpaceAlphaState.value)
                .fillMaxWidth()
                .wrapContentHeight()
                .background(color = subBackground())
                .clickableWithoutRipple {
                    scope.launch {
                        scrollState.scrollTo(0)
                    }
                }
        ) {
            Spacer(modifier = Modifier.height(12.dp))
            APText(
                modifier = Modifier.padding(start = 12.dp),
                text = "테스트 향수 1996 Reissue",
                fontSize = 24.sp,
                fontType = FontType.Bold
            )
            APText(
                modifier = Modifier.padding(start = 12.dp),
                text = "테스트 브랜드",
                fontSize = 12.sp,
                fontColor = subTextColor()
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
private fun PerfumeDetailInformationContent() {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // todo : 데이터 입력 후 구현
    }
}

@Composable
private fun PerfumeDetailReviewsContent() {
    Column(
        modifier = Modifier
            .padding(horizontal = 12.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Column(
            modifier = Modifier
                .padding(60.dp)
                .align(CenterHorizontally)
        ) {
            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = "4.32",
                fontType = FontType.Bold,
                fontSize = 48.sp,
                fontColor = mainColor()
            )

            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = stringResource(
                    id = R.string.review_count,
                    28
                ),
                fontSize = 12.sp,
                fontColor = subTextColor()
            )
        }

        GradientIconButton(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(50.dp),
            icon = painterResource(R.drawable.ic_write),
            text = stringResource(id = R.string.write_review),
            fontSize = 16.sp
        ) {

        }

        Spacer(modifier = Modifier.height(24.dp))

        APAppendedText(
            modifier = Modifier
                .padding(
                    vertical = 12.dp
                ),
            annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = mainTextColor()
                    )
                ) {
                    append(
                        stringResource(
                            id = R.string.best
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = subTextColor()
                    )
                ) {
                    append(" ")
                    append(
                        stringResource(
                            id = R.string.review
                        )
                    )
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        Review(
            modifier = Modifier,
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        Spacer(modifier = Modifier.height(24.dp))

        APAppendedText(
            modifier = Modifier
                .padding(
                    vertical = 12.dp
                ),
            annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = mainTextColor()
                    )
                ) {
                    append(
                        stringResource(
                            id = R.string.perfumer
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = subTextColor()
                    )
                ) {
                    append(" ")
                    append(
                        stringResource(
                            id = R.string.review
                        )
                    )
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        RoundedCornerButton(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(50.dp),
            text = stringResource(
                id = R.string.go_for_more_perfumer_reviews
            )
        ) {

        }

        Spacer(modifier = Modifier.height(36.dp))

        APAppendedText(
            modifier = Modifier
                .padding(
                    vertical = 12.dp
                ),
            annotatedString = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = mainTextColor()
                    )
                ) {
                    append(
                        stringResource(
                            id = R.string.normal
                        )
                    )
                }
                withStyle(
                    style = SpanStyle(
                        color = subTextColor()
                    )
                ) {
                    append(" ")
                    append(
                        stringResource(
                            id = R.string.review
                        )
                    )
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        Review(
            modifier = Modifier
                .padding(vertical = 4.dp),
            score = 4.32f,
            perfumeName = "TestPerfume",
            image = painterResource(id = R.drawable.perfume_test_1),
            title = "Test Title",
            body = "Test Body",
            author = "Test Author",
            authorImage = painterResource(id = R.drawable.ad_banner_2),
            hit = 6554,
            recommend = 32
        )

        RoundedCornerButton(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(50.dp),
            text = stringResource(id = R.string.go_for_more_normal_reviews)
        ) {

        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
private fun ContentButton(
    modifier: Modifier,
    content: String,
    contentState: MutableState<ContentState>,
    onClick: () -> Unit
) {
    val textColorState =
        animateColorAsState(
            targetValue = if (contentState.value == InformationContent && content ==
                stringResource(
                    id = R.string.perfume_information
                )
            ) Color.White else if (contentState.value == ReviewContent && content ==
                stringResource(
                    id = R.string.perfume_review
                )
            ) Color.White else mainTextColor()
        )
    val buttonColorState = animateColorAsState(
        targetValue = if (contentState.value == InformationContent && content ==
            stringResource(
                id = R.string.perfume_information
            )
        ) mainColor() else if (contentState.value == ReviewContent && content ==
            stringResource(
                id = R.string.perfume_review
            )
        ) mainColor() else contentBackground()
    )

    RoundedCornerButton(
        modifier = modifier
            .height(40.dp),
        text = content,
        backgroundColor = buttonColorState.value,
        textColor = textColorState.value
    ) {
        onClick()
    }
}

@Preview(showBackground = true)
@Composable
private fun PerfumeDetailReviewsPreview() {
    PerfumeDetailReviewsContent()
}