package com.jangjh123.allpouse_android.ui.screen.detail.review_detail

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.main.home.dummyAds
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ReviewDetailScreen() {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = background()
            )
            .verticalScroll(
                state = scrollState
            )
            .nestedScroll(object : NestedScrollConnection {
                override suspend fun onPostFling(
                    consumed: Velocity,
                    available: Velocity
                ): Velocity {
                    return super.onPostFling(consumed, available)
                }

                override suspend fun onPreFling(available: Velocity): Velocity {
                    return super.onPreFling(available)
                }
            })
    ) {
        APText(
            modifier = Modifier
                .padding(top = 24.dp, start = 24.dp, bottom = 12.dp),
            text = "테스트 리뷰 제목입니다.",
            fontSize = 20.sp,
            fontType = FontType.Bold,
            lines = 1
        )

        Box(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp
                )
                .fillMaxWidth()

        ) {
            Row {
                Image(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .size(48.dp),
                    painter = painterResource(
                        id = R.drawable.ad_banner_0
                    ),
                    contentDescription = "profileImage",
                    contentScale = ContentScale.FillBounds
                )

                Column(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .align(CenterVertically)
                ) {
                    APText(
                        text = "테스트아이디",
                        fontSize = 14.sp
                    )
                    APText(
                        text = "2022.11.08 16:27",
                        fontSize = 12.sp,
                        fontColor = subTextColor()
                    )
                }
            }


            Row(
                modifier = Modifier
                    .align(BottomEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .size(12.dp)
                        .align(CenterVertically),
                    painter = painterResource(id = R.drawable.ic_viewed),
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
                    painter = painterResource(id = R.drawable.ic_filled_heart),
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

        Divider(
            modifier = Modifier
                .padding(
                    vertical = 12.dp,
                    horizontal = 12.dp
                ),
            thickness = 0.5.dp,
            color = subBackground()
        )

        Row(
            modifier = Modifier
                .align(CenterHorizontally)
        ) {
            Perfume(
                modifier = Modifier
                    .padding(8.dp),
                perfumeName = "테스트 향수",
                brandName = "테스트 브랜드",
                image = painterResource(id = R.drawable.perfume_test_0),
                keywordCount = 3
            )

            val scope = rememberCoroutineScope()

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(CenterVertically)
            ) {
                val startWhenResumeValue0 = remember { mutableStateOf(false) }
                val startWhenResumeValue1 = remember { mutableStateOf(false) }
                val startWhenResumeValue2 = remember { mutableStateOf(false) }
                val startWhenResumeValue3 = remember { mutableStateOf(false) }
                val startWhenResumeValue4 = remember { mutableStateOf(false) }
                val value0 =
                    animateDpAsState(targetValue = if (startWhenResumeValue0.value) 80.dp else 0.dp)
                val value1 =
                    animateDpAsState(targetValue = if (startWhenResumeValue1.value) 60.dp else 0.dp)
                val value2 =
                    animateDpAsState(targetValue = if (startWhenResumeValue2.value) 90.dp else 0.dp)
                val value3 =
                    animateDpAsState(targetValue = if (startWhenResumeValue3.value) 40.dp else 0.dp)
                val value4 =
                    animateDpAsState(targetValue = if (startWhenResumeValue4.value) 70.dp else 0.dp)

                LaunchedEffect(null) { // Test-Only
                    scope.launch {
                        delay(100L)
                        startWhenResumeValue0.value = true
                        delay(90L)
                        startWhenResumeValue1.value = true
                        delay(80L)
                        startWhenResumeValue2.value = true
                        delay(70L)
                        startWhenResumeValue3.value = true
                        delay(60L)
                        startWhenResumeValue4.value = true
                    }
                }

                AnimatedSpecValueBar(
                    modifier = Modifier,
                    specName = "지속력",
                    specValue = value0
                )
                AnimatedSpecValueBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    specName = "테스트",
                    specValue = value1
                )
                AnimatedSpecValueBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    specName = "시원함",
                    specValue = value2
                )
                AnimatedSpecValueBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    specName = "따뜻함",
                    specValue = value3
                )
                AnimatedSpecValueBar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    specName = "가성비",
                    specValue = value4
                )

                APText(
                    modifier = Modifier
                        .padding(8.dp)
                        .align(CenterHorizontally),
                    text = "4.32",
                    fontType = FontType.Bold,
                    fontSize = 24.sp,
                    fontColor = mainColor()
                )
            }
        }

        HorizontalPager(
            modifier = Modifier
                .padding(
                    top = 36.dp
                )
                .fillMaxWidth()
                .height(200.dp),
            state = pagerState,
            count = dummyAds.size
        ) { index ->
            val item = dummyAds[index]
            Image(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .clip(
                        shape = RoundedCornerShape(24.dp)
                    )
                    .fillMaxSize(),
                painter = painterResource(id = item),
                contentDescription = "reviewImage",
                contentScale = ContentScale.Crop
            )
        }

        HorizontalPagerIndicator(
            modifier = Modifier
                .padding(
                    top = 12.dp,
                    bottom = 36.dp
                )
                .align(CenterHorizontally),
            pagerState = pagerState,
            activeColor = mainColor(),
            inactiveColor = Color.LightGray
        )

        Box(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp
                )
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(24.dp)
                )
                .background(
                    color = subBackground()
                )
        ) {
            APText(
                modifier = Modifier
                    .padding(24.dp),
                text = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
                fontSize = 14.sp
            )
        }

        val recommendState = remember { mutableStateOf(false) }
        val iconState = remember { mutableStateOf(R.drawable.ic_empty_heart) }
        val recommendTextState = remember { mutableStateOf(R.string.recommend) }
        val recommendTextColorState =
            animateColorAsState(targetValue = if (recommendState.value) mainColor() else Color.White)
        val iconTintState =
            animateColorAsState(targetValue = if (recommendState.value) mainColor() else subTextColor())

        RoundedCornerIconButton(
            modifier = Modifier
                .padding(
                    top = 24.dp
                )
                .align(CenterHorizontally)
                .height(40.dp)
                .width(110.dp),
            text = stringResource(id = recommendTextState.value),
            icon = painterResource(iconState.value),
            textColor = recommendTextColorState.value,
            iconTint = iconTintState.value
        ) {
            if (!recommendState.value) {
                recommendState.value = true
                iconState.value = R.drawable.ic_filled_heart
                recommendTextState.value = R.string.did_recommend
            } else {
                recommendState.value = false
                iconState.value = R.drawable.ic_empty_heart
                recommendTextState.value = R.string.recommend
            }
        }

        Divider(
            modifier = Modifier
                .padding(
                    vertical = 24.dp,
                    horizontal = 12.dp
                ),
            thickness = 0.5.dp,
            color = subBackground()
        )

        APText(
            modifier = Modifier
                .padding(
                    horizontal = 24.dp
                )
                .padding(
                    bottom = 12.dp
                ),
            text = stringResource(
                id = R.string.comment
            ),
            fontSize = 20.sp
        )

        dummyReviewComments.forEach {
            Comment(
                modifier = Modifier
                    .padding(horizontal = 24.dp),
                comment = it
            )

            Divider(
                modifier = Modifier
                    .padding(
                        vertical = 12.dp,
                        horizontal = 12.dp
                    ),
                thickness = 0.5.dp,
                color = subBackground()
            )
        }
    }
}

val dummyReviewComments = listOf(
    DummyComment.DummyReviewComment(
        userName = "테스트 작성자",
        date = "2022.12.15 18:53",
        userProfileImage = R.drawable.ad_banner_0,
        body = "테스트 댓글입니다.",
        commentsInComment = ArrayList<DummyComment.DummyCommentInReviewComment>().apply {
            add(
                DummyComment.DummyCommentInReviewComment(
                    userName = "테스트 작성자2",
                    date = "2022.12.16 18:33",
                    userProfileImage = R.drawable.ad_banner_1,
                    body = "테스트 답글입니다."
                )
            )
            add(
                DummyComment.DummyCommentInReviewComment(
                    userName = "테스트 작성자2",
                    date = "2022.12.16 18:33",
                    userProfileImage = R.drawable.ad_banner_1,
                    body = "테스트 답글입니다."
                )
            )
        }
    ),
    DummyComment.DummyReviewComment(
        userName = "테스트 작성자",
        date = "2022.12.15 18:53",
        userProfileImage = R.drawable.ad_banner_0,
        body = "테스트 댓글입니다.",
        commentsInComment = ArrayList()
    )
)


@Composable
private fun AnimatedSpecValueBar(modifier: Modifier, specName: String, specValue: State<Dp>) {
    Column(
        modifier = modifier
    ) {
        APText(
            text = specName,
            fontSize = 12.sp,
            fontColor = mainTextColor()
        )

        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .width(100.dp)
                .height(8.dp)
                .background(
                    color = contentBackground()
                )
        ) {

            Box(
                modifier = Modifier
                    .width(specValue.value)
                    .fillMaxHeight()
                    .background(
                        color = mainColor()
                    )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ReviewDetailScreen()
}