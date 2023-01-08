package com.jangjh123.allpouse_android.ui.screen.main.home

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.data.model.AdBanner
import com.jangjh123.allpouse_android.data.model.Brand
import com.jangjh123.allpouse_android.data.model.Perfume
import com.jangjh123.allpouse_android.data.model.PostWithBoardName
import com.jangjh123.allpouse_android.data.remote.NoParameterRequiredData
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.PerfumeDetailActivity
import com.jangjh123.allpouse_android.ui.screen.main.MainViewModel
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_WIDTH_DP
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.delay

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
fun HomeScreen(
    viewModel: MainViewModel,
    risingPerfumePagingItems: LazyPagingItems<List<Perfume>>
) {
    val adPagerState = rememberPagerState()
    val context = LocalContext.current

    LaunchedEffect(null) {
        with(viewModel) {
            getHomeScreenData(NoParameterRequiredData.AdBannerList)
            getHomeScreenData(NoParameterRequiredData.RecommendedPerfumeList)
            getHomeScreenData(NoParameterRequiredData.BestPostList)
            getHomeScreenData(NoParameterRequiredData.PopularBrandList)
//            getRisingPerfumeList()
        }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calculatedHeight)
                ) {
                    val adBannerListState = viewModel.adBannerListState.collectAsState(
                        initial = UiState.OnLoading
                    )
                    when (adBannerListState.value) {
                        is UiState.OnLoading -> {
                            Loading(
                                modifier = Modifier
                                    .align(Center)
                            )
                        }
                        is UiState.OnSuccess -> {
                            val adBannerList =
                                (adBannerListState.value as UiState.OnSuccess).data as List<*>
                            HorizontalPager(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(calculatedHeight),
                                state = adPagerState,
                                count = adBannerList.size
                            ) { idx ->
                                AsyncImage(
                                    modifier = Modifier.fillMaxSize(),
                                    model = (adBannerList[idx] as AdBanner).image,
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
                                while (true) {
                                    delay(3000L)
                                    if (adPagerState.currentPage == adBannerList.lastIndex) {
                                        adPagerState.animateScrollToPage(0)
                                    } else {
                                        adPagerState.animateScrollToPage(adPagerState.currentPage + 1)
                                    }
                                }
                            }
                        }
                        is UiState.OnFailure -> {
                            RetryBlock(
                                modifier = Modifier
                                    .align(Center)
                            ) {
                                viewModel.getHomeScreenData(
                                    data = NoParameterRequiredData.AdBannerList
                                )
                            }
                        }
                    }
                }

                APText(
                    modifier = Modifier.padding(
                        horizontal = 12.dp, vertical = 16.dp
                    ), text = stringResource(
                        id = R.string.taste_keyword
                    ), fontSize = 18.sp, fontType = FontType.Bold
                )

                LazyRow(
                    modifier = Modifier
                        .wrapContentSize(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(
                        horizontal = 12.dp
                    )
                ) {
                    items(dummyTasteKeyword) { tasteKeyword ->
                        Keyword(
                            modifier = Modifier,
                            tasteKeyword
                        )
                    }

                    item {
                        Box(
                            modifier = Modifier
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
                    modifier = Modifier.height(36.dp)
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
                    }, fontSize = 18.sp, fontType = FontType.Bold
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                ) {
                    val recommendedPerfumeListState =
                        viewModel.recommendedPerfumeListState.collectAsState(
                            initial = UiState.OnLoading
                        )
                    when (recommendedPerfumeListState.value) {
                        is UiState.OnLoading -> {
                            Loading(
                                modifier = Modifier
                                    .align(Center)
                            )
                        }
                        is UiState.OnSuccess -> {
                            val recommendedPerfumeList =
                                (recommendedPerfumeListState.value as UiState.OnSuccess).data as List<*>

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
                                items(recommendedPerfumeList) { perfume ->
                                    perfume as Perfume
                                    RecommendedPerfume(
                                        modifier = Modifier,
                                        perfumeName = perfume.perfumeName,
                                        brandName = perfume.brandName,
                                        imagePath = perfume.imagePath?.get(0) ?: "",
                                        keywordCount = perfume.id
                                    ) {
                                        context.startActivity(
                                            Intent(
                                                context,
                                                PerfumeDetailActivity::class.java
                                            ).apply {
                                                this.putExtra("perfumeId", perfume.id)
                                            }
                                        )
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
                                    .align(Center)
                            ) {
                                viewModel.getHomeScreenData(
                                    data = NoParameterRequiredData.RecommendedPerfumeList
                                )
                            }
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(36.dp)
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
                }, fontSize = 18.sp, fontType = FontType.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                val bestPostListState = viewModel.bestPostListState.collectAsState(
                    initial = UiState.OnLoading
                )

                when (bestPostListState.value) {
                    is UiState.OnLoading -> {
                        Loading(
                            modifier = Modifier
                                .align(Center)
                        )
                    }
                    is UiState.OnSuccess -> {
                        val bestPostList =
                            (bestPostListState.value as UiState.OnSuccess).data as List<*>

                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(4) { index ->
                                ((bestPostList[index] as PostWithBoardName).also { post ->
                                    PostWithBoardName(
                                        modifier = Modifier,
                                        board = post.board,
                                        postName = post.title,
                                        like = post.hitCount
                                    )
                                })
                            }

                            RoundedCornerButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                text = stringResource(
                                    id = R.string.go_for_more_posts
                                )
                            ) {

                            }
                        }
                    }
                    is UiState.OnFailure -> {
                        RetryBlock(
                            modifier = Modifier
                                .align(Center)
                        ) {
                            viewModel.getHomeScreenData(
                                data = NoParameterRequiredData.BestPostList
                            )
                        }
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(36.dp)
            )

            APText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ),
                text = stringResource(R.string.age_gender_best, 20, "남성"),
                fontSize = 18.sp,
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
                modifier = Modifier.height(36.dp)
            )

            APText(
                modifier = Modifier.padding(
                    horizontal = 12.dp, vertical = 16.dp
                ),
                text = stringResource(id = R.string.popular_brands),
                fontSize = 18.sp,
                fontType = FontType.Bold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val popularBrandListState = viewModel.popularBrandListState.collectAsState(
                    initial = UiState.OnLoading
                )

                when (popularBrandListState.value) {
                    is UiState.OnLoading -> {
                        Loading(
                            modifier = Modifier
                                .align(Center)
                        )
                    }
                    is UiState.OnSuccess -> {
                        val popularBrandList =
                            (popularBrandListState.value as UiState.OnSuccess).data as List<*>
                        Column(
                            modifier = Modifier
                                .padding(
                                    horizontal = 12.dp
                                ),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            repeat(3) { index ->
                                ((popularBrandList[index] as Brand).also { brand ->
                                    brand.imagePath?.get(0)?.let {
                                        BrandComposable(
                                            modifier = Modifier,
                                            brandName = brand.name,
                                            brandImage = it,
                                            brandPerfumeCount = brand.id,
                                            brandHit = brand.id
                                        ) {
                                        }
                                    }
                                })
                            }

                            RoundedCornerButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                text = stringResource(
                                    id = R.string.go_for_more_brands
                                )
                            ) {

                            }
                        }
                    }
                    is UiState.OnFailure -> {
                        RetryBlock(
                            modifier = Modifier
                                .align(Center)
                        ) {
                            viewModel.getHomeScreenData(
                                data = NoParameterRequiredData.PopularBrandList
                            )
                        }
                    }
                }
            }
        }

        when (risingPerfumePagingItems.loadState.refresh) {
            is LoadState.Loading -> {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        Loading(
                            modifier = Modifier
                                .align(Center)
                        )
                    }
                }
            }
            is LoadState.Error -> {

            }
            else -> {
                item {
                    Spacer(
                        modifier = Modifier
                            .height(36.dp)
                    )

                    APText(
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 16.dp
                        ),
                        text = stringResource(
                            id = R.string.rising_perfumes
                        ),
                        fontSize = 18.sp,
                        fontType = FontType.Bold
                    )
                }

                items(risingPerfumePagingItems) { perfumes ->
                    FlowRow(
                        modifier = Modifier,
                        mainAxisSize = SizeMode.Expand,
                        mainAxisAlignment = FlowMainAxisAlignment.SpaceEvenly
                    ) {
                        perfumes?.forEach { perfume ->
                            PerfumeComposable(
                                modifier = Modifier,
                                perfume = perfume
                            )
                        }
                    }
                }
            }
        }

        if (risingPerfumePagingItems.loadState.append is LoadState.Error) {
            if ((risingPerfumePagingItems.loadState.append as LoadState.Error).error.message!!.contains(
                    "500"
                )
            ) {
                item {
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
                }
            } else {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ) {
                        RetryBlock(
                            modifier = Modifier
                                .align(Center)
                        ) {
                            risingPerfumePagingItems.retry()
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
}