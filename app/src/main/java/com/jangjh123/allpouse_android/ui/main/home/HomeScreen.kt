package com.jangjh123.allpouse_android.ui.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterEnd
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APAppendedText
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.FontType
import com.jangjh123.allpouse_android.ui.component.ReviewListItem
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

val dummyAds = listOf(
    R.drawable.ad_banner_2,
    R.drawable.ad_banner_1,
    R.drawable.ad_banner_0,
    R.drawable.ad_banner_1,
    R.drawable.ad_banner_2
)

val dummyTasteKeyword = listOf(
    "남성적인",
    "고급스러운",
    "시크한",
    "오래 가는",
    "대용량"
)

data class DummyRecommended(
    val image: Int,
    val perfumeName: String,
    val brandName: String,
    val keywordCount: Int,
)

val dummyPerfumesForYou = listOf(
    DummyRecommended(
        R.drawable.perfume_test_0,
        "Test0",
        "Hexadecimal",
        5
    ),
    DummyRecommended(
        R.drawable.perfume_test_1,
        "Test1",
        "color values",
        5
    ),
    DummyRecommended(
        R.drawable.perfume_test_2,
        "Test2",
        "supported in",
        4
    ),
    DummyRecommended(
        R.drawable.perfume_test_0,
        "Test0",
        "all browsers.",
        4
    ),
    DummyRecommended(
        R.drawable.perfume_test_1,
        "Test1",
        "Hexadecimal",
        3
    )
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val adPagerState = rememberPagerState()
    val localDensity = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = background())
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val calculatedHeight = (screenWidth / 16F) * 9F

        Box(modifier = Modifier.wrapContentSize()) {
            val adPagerScope = rememberCoroutineScope()
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calculatedHeight),
                state = adPagerState,
                count = dummyAds.size
            ) { idx ->
                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painterResource(id = dummyAds[idx]),
                    contentDescription = "adBannerImage",
                    contentScale = ContentScale.FillHeight
                )
            }


            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .width(48.dp)
                    .height(24.dp)
                    .background(color = Color(0x77000000))
                    .align(Alignment.BottomEnd)
            ) {
                APText(
                    modifier = Modifier.align(Center),
                    text = "${adPagerState.currentPage + 1} / ${adPagerState.pageCount}",
                    fontColor = Color.White,
                    fontSize = 12.sp
                )
            }

            LaunchedEffect(null) {
                adPagerScope.launch {
                    while (true) {
                        delay(3000L)
                        if (adPagerState.currentPage == dummyAds.lastIndex) {
                            adPagerState.animateScrollToPage(0)
                        } else {
                            adPagerState.animateScrollToPage(adPagerState.currentPage + 1)
                        }
                    }
                }
            }
        }

        APText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            text = stringResource(id = R.string.taste_keyword),
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 10.dp)
        ) {
            items(dummyTasteKeyword) { tasteKeyword ->
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .width(100.dp)
                        .height(36.dp)
                        .background(contentBackground())
                ) {
                    APText(
                        modifier = Modifier.align(Center),
                        text = tasteKeyword,
                        fontColor = mainTextColor(),
                        fontSize = 14.sp
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .width(100.dp)
                        .height(36.dp)
                        .background(contentBackground())
                ) {
                    Row(modifier = Modifier.align(Center)) {
                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .align(CenterVertically),
                            painter = painterResource(id = R.drawable.ic_edit),
                            contentDescription = "editKeyword",
                            tint = subTextColor()
                        )

                        APText(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.edit),
                            fontColor = subTextColor()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        APAppendedText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = mainTextColor())) {
                    append("지호")
                }

                withStyle(style = SpanStyle(color = subTextColor())) {
                    append(stringResource(id = R.string.perfumes_recommended))
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        val itemHeight = remember { mutableStateOf(0.dp) }

        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 8.dp)
        ) {
            items(dummyPerfumesForYou) { perfume ->
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(160.dp)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = subBackground())
                        .onGloballyPositioned { coordinates ->
                            itemHeight.value = with(localDensity) {
                                coordinates.size.height.toDp()
                            }
                        }
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
                            painter = painterResource(id = perfume.image),
                            contentDescription = "perfumeImage",
                            contentScale = ContentScale.FillBounds
                        )
                        APText(
                            modifier = Modifier.padding(
                                top = 4.dp,
                                start = 10.dp,
                                end = 10.dp
                            ),
                            text = perfume.perfumeName,
                            fontColor = mainTextColor(),
                            fontSize = 14.sp
                        )
                        APText(
                            modifier = Modifier
                                .padding(horizontal = 10.dp),
                            text = perfume.brandName,
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
                                    append("${perfume.keywordCount}")
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

            item {
                Box(
                    modifier = Modifier
                        .width(136.dp)
                        .height(itemHeight.value)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Center)
                            .wrapContentSize()
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.CenterHorizontally),
                            painter = painterResource(id = R.drawable.ic_arrow_foward),
                            tint = subTextColor(),
                            contentDescription = "goForMorePerfumes"
                        )

                        APText(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.show_more),
                            fontColor = subTextColor()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        APAppendedText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = mainTextColor())) {
                    append(stringResource(id = R.string.app_korean))
                }

                withStyle(style = SpanStyle(color = subTextColor())) {
                    append(" ")
                    append(stringResource(id = R.string.best_post))
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        BestPerfumerTalkPost(
            "조향사게시판",
            postName = "첫 번째 테스트 포스트",
            like = 33
        )
        BestPerfumerTalkPost(
            "조향사게시판",
            postName = "두 번째 테스트 포스트",
            like = 31
        )
        BestPerfumerTalkPost(
            "자유게시판",
            postName = "세 번째 테스트 포스트",
            like = 22
        )
        BestPerfumerTalkPost(
            "자유게시판",
            postName = "네 번째 테스트 포스트",
            like = 19
        )
        BestPerfumerTalkPost(
            "조향사게시판",
            postName = "다섯 번째 테스트 포스트",
            like = 15
        )

        Box(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(contentBackground())
        ) {
            APText(
                modifier = Modifier.align(Center),
                text = stringResource(id = R.string.go_for_more_posts),
                fontColor = mainTextColor(),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        APText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            text = stringResource(R.string.age_gender_best, 20, "남성"),
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        LazyRow(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 8.dp)
        ) {
            items(dummyPerfumesForYou) { perfume ->
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(160.dp)
                        .padding(horizontal = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(color = subBackground())
                        .onGloballyPositioned { coordinates ->
                            itemHeight.value = with(localDensity) {
                                coordinates.size.height.toDp()
                            }
                        }
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
                            painter = painterResource(id = perfume.image),
                            contentDescription = "perfumeImage",
                            contentScale = ContentScale.FillBounds
                        )
                        APText(
                            modifier = Modifier.padding(
                                top = 4.dp,
                                start = 10.dp,
                                end = 10.dp
                            ),
                            text = perfume.perfumeName,
                            fontColor = mainTextColor(),
                            fontSize = 14.sp
                        )
                        APText(
                            modifier = Modifier
                                .padding(horizontal = 10.dp),
                            text = perfume.brandName,
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
                                    append("${perfume.keywordCount}")
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

            item {
                Box(
                    modifier = Modifier
                        .width(136.dp)
                        .height(itemHeight.value)
                ) {
                    Column(
                        modifier = Modifier
                            .align(Center)
                            .wrapContentSize()
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(36.dp)
                                .align(Alignment.CenterHorizontally),
                            painter = painterResource(id = R.drawable.ic_arrow_foward),
                            tint = subTextColor(),
                            contentDescription = "goForMorePerfumes"
                        )

                        APText(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(id = R.string.show_more),
                            fontColor = subTextColor()
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        APText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            text = stringResource(id = R.string.popular_reviews),
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        ReviewListItem(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            score = 4.7f,
            perfumeName = "TestPerfume",
            image = R.drawable.ad_banner_0,
            title = "테스트 향수입니다.",
            body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
            author = "TestAuthor0",
            authorImage = R.drawable.ad_banner_1,
            hit = 3312,
            recommend = 43
        )

        ReviewListItem(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            score = 4.7f,
            perfumeName = "TestPerfume",
            image = R.drawable.ad_banner_1,
            title = "테스트 향수입니다.",
            body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
            author = "TestAuthor1",
            authorImage = R.drawable.ad_banner_1,
            hit = 3312,
            recommend = 43
        )

        ReviewListItem(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            score = 4.7f,
            perfumeName = "TestPerfume",
            image = R.drawable.ad_banner_2,
            title = "테스트 향수입니다.",
            body = "사람들의 내 내 봅니다. 까닭이요, 벌레는 나는 듯합니다. 아무 우는 사람들의 잠, 다 별이 이름을 까닭입니다. 소녀들의 새겨지는 않은 하늘에는 버리었습니다. 이름과, 하나의 벌써 토끼, 새겨지는 별이 그리고 것은 없이 있습니다. 했던 위에 아름다운 덮어 밤을 그러나 이름과 까닭이요, 봅니다. 이름자를 어머니, 위에 별 나의 것은 계절이 버리었습니다. 나는 써 하나에 그리고 동경과 가을로 멀듯이, 계십니다. 위에 이네들은 가득 까닭입니다. 못 피어나듯이 아름다운 부끄러운 지나가는 잠, 봅니다. 이름과, 가을 별 아름다운 흙으로 별빛이 봅니다.",
            author = "TestAuthor2",
            authorImage = R.drawable.ad_banner_1,
            hit = 3312,
            recommend = 43
        )

        Box(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 20.dp)
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(contentBackground())
        ) {
            APText(
                modifier = Modifier.align(Center),
                text = stringResource(id = R.string.go_for_more_reviews),
                fontColor = mainTextColor(),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        APText(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 16.dp),
            text = stringResource(id = R.string.popular_perfumes),
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_0,
                perfumeName = "TestPerfume0",
                brandName = "Samsung"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_1,
                perfumeName = "TestPerfume1",
                brandName = "Google"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_2,
                perfumeName = "TestPerfume2",
                brandName = "Nokia"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_0,
                perfumeName = "TestPerfume0",
                brandName = "Samsung"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_1,
                perfumeName = "TestPerfume1",
                brandName = "Google"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_2,
                perfumeName = "TestPerfume2",
                brandName = "Nokia"
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        ) {
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_0,
                perfumeName = "TestPerfume0",
                brandName = "Samsung"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_1,
                perfumeName = "TestPerfume1",
                brandName = "Google"
            )
            PopularPerfume(
                modifier = Modifier.weight(1f),
                image = R.drawable.perfume_test_2,
                perfumeName = "TestPerfume2",
                brandName = "Nokia"
            )
        }

        Box(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 12.dp)
                .clip(RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .height(50.dp)
                .background(contentBackground())
        ) {
            APText(
                modifier = Modifier.align(Center),
                text = stringResource(id = R.string.go_for_more_perfumes),
                fontColor = mainTextColor(),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun PopularPerfume(modifier: Modifier, image: Int, perfumeName: String, brandName: String) {
    Column(modifier = modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(shape = RoundedCornerShape(12.dp))
                .background(contentBackground())
                .padding(10.dp),
            painter = painterResource(id = image),
            contentDescription = "perfumeImage",
            contentScale = ContentScale.FillBounds
        )
        APText(
            modifier = Modifier.padding(
                top = 4.dp,
                start = 8.dp,
                end = 8.dp
            ),
            text = perfumeName,
            fontColor = mainTextColor(),
            fontSize = 12.sp
        )
        APText(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp),
            text = brandName,
            fontColor = subTextColor(),
            fontSize = 10.sp
        )
    }
}

@Composable
fun BestPerfumerTalkPost(
    board: String,
    postName: String,
    like: Int,
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(12.dp))
            .background(color = subBackground())
    ) {

        Row(Modifier.fillMaxHeight()) {
            APText(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .padding(vertical = 8.dp),
                text = board,
                fontSize = 10.sp,
                fontColor = mainColor()
            )

            APText(
                modifier = Modifier
                    .padding(start = 8.dp)
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
                    .padding(4.dp)
                    .align(CenterVertically)
                    .size(12.dp),
                painter = painterResource(id = R.drawable.ic_filled_heart),
                contentDescription = "postLike",
                tint = subTextColor()
            )

            APText(
                modifier = Modifier
                    .align(CenterVertically)
                    .padding(end = 12.dp),
                text = "$like",
                fontColor = subTextColor(),
                fontSize = 10.sp
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    HomeScreen()
}