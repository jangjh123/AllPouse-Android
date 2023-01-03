package com.jangjh123.allpouse_android.ui.screen.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.jangjh123.allpouse_android.data.model.Perfume
import com.jangjh123.allpouse_android.data.remote.NoParameterRequiredData
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.main.MainViewModel
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_WIDTH_DP
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
    "남성적인", "고급스러운", "시크한", "오래 가는", "대용량"
)

data class DummyRecommended(
    val image: Int,
    val perfumeName: String,
    val brandName: String,
    val keywordCount: Int,
)

val dummyPerfumesForYou = listOf(
    DummyRecommended(
        R.drawable.perfume_test_0, "Test0", "Hexadecimal", 5
    ), DummyRecommended(
        R.drawable.perfume_test_1, "Test1", "color values", 5
    ), DummyRecommended(
        R.drawable.perfume_test_2, "Test2", "supported in", 4
    ), DummyRecommended(
        R.drawable.perfume_test_0, "Test0", "all browsers.", 4
    ), DummyRecommended(
        R.drawable.perfume_test_1, "Test1", "Hexadecimal", 3
    )
)

var PERFUME_ITEM_HEIGHT = 0.dp

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    val adPagerState = rememberPagerState()
    val viewModel = composableActivityViewModel<MainViewModel>()

    with(viewModel) {
        getHomeScreenData(NoParameterRequiredData.RecommendedPerfumeList)
        getHomeScreenData(NoParameterRequiredData.BestPostList)
        getHomeScreenData(NoParameterRequiredData.PopularBrandList)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = background()
            )
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = background()
                    )
            ) {
                val screenWidth = SCREEN_WIDTH_DP
                val calculatedHeight = (screenWidth / 16F) * 9F

                Box(
                    modifier = Modifier.wrapContentSize()
                ) {
                    val adPagerScope = rememberCoroutineScope()
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(calculatedHeight),
                        state = adPagerState,
                        count = dummyAds.size
                    ) { idx ->
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(
                                id = dummyAds[idx]
                            ),
                            contentDescription = "adBannerImage",
                            contentScale = ContentScale.FillHeight
                        )
                    }


                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(
                                shape = RoundedCornerShape(20.dp)
                            )
                            .width(48.dp)
                            .height(24.dp)
                            .background(
                                color = darkFilter()
                            )
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
                    modifier = Modifier.padding(
                        horizontal = 12.dp, vertical = 16.dp
                    ), text = stringResource(
                        id = R.string.taste_keyword
                    ), fontSize = 20.sp, fontType = FontType.Bold
                )

                LazyRow(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(
                            horizontal = 10.dp
                        )
                ) {
                    items(dummyTasteKeyword) { tasteKeyword ->
                        Keyword(
                            modifier = Modifier.padding(
                                horizontal = 4.dp
                            ), tasteKeyword
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
                                .padding(
                                    horizontal = 4.dp
                                )
                                .clip(
                                    shape = RoundedCornerShape(24.dp)
                                )
                                .width(100.dp)
                                .height(36.dp)
                                .background(contentBackground())
                        ) {
                            Row(modifier = Modifier.align(Center)) {
                                Icon(
                                    modifier = Modifier
                                        .size(16.dp)
                                        .align(CenterVertically),
                                    painter = painterResource(
                                        id = R.drawable.ic_edit
                                    ),
                                    contentDescription = "editKeyword",
                                    tint = subTextColor()
                                )

                                APText(
                                    modifier = Modifier.padding(4.dp), text = stringResource(
                                        id = R.string.edit
                                    ), fontColor = subTextColor()
                                )
                            }
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                APAppendedText(
                    modifier = Modifier.padding(
                        horizontal = 12.dp, vertical = 16.dp
                    ), annotatedString = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = mainTextColor()
                            )
                        ) {
                            append("지호")
                        }

                        withStyle(
                            style = SpanStyle(
                                color = subTextColor()
                            )
                        ) {
                            append(
                                stringResource(
                                    id = R.string.perfumes_recommended
                                )
                            )
                        }
                    }, fontSize = 20.sp, fontType = FontType.Bold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(
                            color = background()
                        )
                ) {
                    val recommendedPerfumeListState =
                        viewModel.recommendedPerfumeListState.collectAsState(
                            initial = UiState.Loading
                        )
                    when (recommendedPerfumeListState.value) {
                        is UiState.Loading -> {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .align(Center),
                                color = mainColor()
                            )
                        }
                        is UiState.OnSuccess -> {
                            LazyRow(
                                modifier = Modifier
                                    .background(
                                        color = background()
                                    ),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(
                                    horizontal = 12.dp
                                )
                            ) {
                                items((recommendedPerfumeListState.value as UiState.OnSuccess).data as List<*>) { perfume ->
                                    perfume as Perfume
                                    Perfume(
                                        modifier = Modifier,
                                        perfumeName = perfume.perfumeName,
                                        brandName = perfume.brandName,
                                        imagePath = perfume.imagePath?.get(0) ?: "",
                                        keywordCount = perfume.id
                                    )
                                }

                                item {
                                    Box(
                                        modifier = Modifier
                                            .width(136.dp)
                                            .height(220.dp)
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
                                                painter = painterResource(
                                                    id = R.drawable.ic_arrow_foward
                                                ),
                                                tint = subTextColor(),
                                                contentDescription = "goForMorePerfumes"
                                            )

                                            APText(
                                                modifier = Modifier.padding(4.dp),
                                                text = stringResource(
                                                    id = R.string.show_more
                                                ),
                                                fontColor = subTextColor()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        is UiState.OnFailure -> {
                            RetryBlock(
                                modifier = Modifier
                                    .align(Center)) {
                                viewModel.getHomeScreenData(NoParameterRequiredData.RecommendedPerfumeList)
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
            APAppendedText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ), annotatedString = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = mainTextColor()
                        )
                    ) {
                        append(
                            stringResource(
                                id = R.string.app_name
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
                                id = R.string.best_post
                            )
                        )
                    }
                }, fontSize = 20.sp, fontType = FontType.Bold
            )

            PostWithBoardName(
                modifier = Modifier.padding(
                    horizontal = 12.dp
                ), "조향사게시판", postName = "첫 번째 테스트 포스트", like = 33
            )
            PostWithBoardName(
                modifier = Modifier.padding(
                    horizontal = 12.dp
                ), "조향사게시판", postName = "두 번째 테스트 포스트", like = 31
            )
            PostWithBoardName(
                modifier = Modifier.padding(
                    horizontal = 12.dp
                ), "자유게시판", postName = "세 번째 테스트 포스트", like = 22
            )
            PostWithBoardName(
                modifier = Modifier.padding(
                    horizontal = 12.dp
                ), "자유게시판", postName = "네 번째 테스트 포스트", like = 19
            )
            PostWithBoardName(
                modifier = Modifier.padding(
                    horizontal = 12.dp
                ), "조향사게시판", postName = "다섯 번째 테스트 포스트", like = 15
            )

            RoundedCornerButton(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(
                    id = R.string.go_for_more_posts
                )
            ) {

            }


            Spacer(
                modifier = Modifier.height(12.dp)
            )

            APText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ),
                text = stringResource(R.string.age_gender_best, 20, "남성"),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            LazyRow(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(
                        horizontal = 8.dp
                    )
            ) {
                items(dummyPerfumesForYou) { perfume ->
                    Box(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(160.dp)
                            .padding(
                                horizontal = 4.dp
                            )
                            .clip(
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(
                                color = subBackground()
                            )
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp, start = 8.dp, end = 8.dp
                                    )
                                    .size(160.dp)
                                    .clip(
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .background(contentBackground())
                                    .padding(10.dp),
                                painter = painterResource(
                                    id = perfume.image
                                ),
                                contentDescription = "perfumeImage",
                                contentScale = ContentScale.FillBounds
                            )
                            APText(
                                modifier = Modifier.padding(
                                    top = 4.dp, start = 10.dp, end = 10.dp
                                ),
                                text = perfume.perfumeName,
                                fontColor = mainTextColor(),
                                fontSize = 14.sp
                            )
                            APText(
                                modifier = Modifier.padding(
                                    horizontal = 10.dp
                                ),
                                text = perfume.brandName,
                                fontColor = subTextColor(),
                                fontSize = 12.sp
                            )
                            APAppendedText(modifier = Modifier
                                .padding(
                                    horizontal = 10.dp
                                )
                                .padding(
                                    bottom = 8.dp
                                ), annotatedString = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = subTextColor(), fontSize = 12.sp
                                    )
                                ) {
                                    append("5개 중")
                                    append(" ")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = mainColor(), fontSize = 12.sp
                                    )
                                ) {
                                    append("${perfume.keywordCount}")
                                }
                                withStyle(
                                    style = SpanStyle(
                                        color = subTextColor(), fontSize = 12.sp
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
                            .height(220.dp)
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
                                painter = painterResource(
                                    id = R.drawable.ic_arrow_foward
                                ),
                                tint = subTextColor(),
                                contentDescription = "goForMorePerfumes"
                            )

                            APText(
                                modifier = Modifier.padding(4.dp), text = stringResource(
                                    id = R.string.show_more
                                ), fontColor = subTextColor()
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            APText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ),
                text = stringResource(id = R.string.popular_brands),
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            Brand(
                modifier = Modifier.padding(
                    vertical = 4.dp, horizontal = 12.dp
                ), brandName = "Versace", brandImage = painterResource(
                    id = R.drawable.brand_test_0
                ), brandPerfumeCount = 13, brandHit = 312
            ) {

            }

            Brand(
                modifier = Modifier.padding(
                    vertical = 4.dp, horizontal = 12.dp
                ), brandName = "Chanel", brandImage = painterResource(
                    id = R.drawable.brand_test_1
                ), brandPerfumeCount = 18, brandHit = 221
            ) {

            }

            Brand(
                modifier = Modifier.padding(
                    vertical = 4.dp, horizontal = 12.dp
                ), brandName = "Lamborghini", brandImage = painterResource(
                    id = R.drawable.brand_test_2
                ), brandPerfumeCount = 3, brandHit = 123
            ) {

            }

            RoundedCornerButton(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(
                    id = R.string.go_for_more_brands
                )
            ) {

            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            APText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ), text = stringResource(
                    id = R.string.popular_perfumes
                ), fontSize = 20.sp, fontType = FontType.Bold
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 12.dp
                    )
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
                    .padding(
                        horizontal = 12.dp
                    )
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
                    .padding(
                        horizontal = 12.dp
                    )
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

            RoundedCornerButton(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(
                    id = R.string.go_for_more_perfumes
                )
            ) {

            }

            Spacer(
                modifier = Modifier.height(36.dp)
            )
        }
    }

    // todo : paging
}


@Composable
fun PopularPerfume(
    modifier: Modifier,
    image: Int,
    perfumeName: String,
    brandName: String,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier
                .size(100.dp)
                .clip(
                    shape = RoundedCornerShape(12.dp)
                )
                .background(contentBackground())
                .padding(10.dp), painter = painterResource(
                id = image
            ), contentDescription = "perfumeImage", contentScale = ContentScale.FillBounds
        )
        APText(
            modifier = Modifier.padding(
                top = 4.dp, start = 8.dp, end = 8.dp
            ), text = perfumeName, fontColor = mainTextColor(), fontSize = 12.sp
        )
        APText(
            modifier = Modifier
                .padding(
                    horizontal = 8.dp
                )
                .padding(
                    bottom = 8.dp
                ), text = brandName, fontColor = subTextColor(), fontSize = 10.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    HomeScreen()
}