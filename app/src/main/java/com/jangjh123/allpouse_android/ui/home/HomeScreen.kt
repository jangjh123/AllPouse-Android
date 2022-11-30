package com.jangjh123.allpouse_android.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
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
import com.jangjh123.allpouse_android.ui.theme.*

val dummyAds = listOf(
    R.drawable.ad_banner_2,
    R.drawable.ad_banner_1,
    R.drawable.ad_banner_0,
    R.drawable.ad_banner_1,
    R.drawable.ad_banner_2
)

val dummyPerfumesForYou = listOf(
    Triple(
        R.drawable.perfume_test_0,
        "Test0",
        "Hexadecimal"
    ),
    Triple(
        R.drawable.perfume_test_1,
        "Test1",
        "color values"
    ),
    Triple(
        R.drawable.perfume_test_2,
        "Test2",
        "supported in"
    ),
    Triple(
        R.drawable.perfume_test_0,
        "Test0",
        "all browsers."
    ),
    Triple(
        R.drawable.perfume_test_1,
        "Test1",
        "Hexadecimal"
    ),
    Triple(
        R.drawable.perfume_test_2,
        "Test2",
        "in all browsers."
    ),
    Triple(
        R.drawable.perfume_test_0,
        "Test0",
        "Hexadecimal"
    ),
    Triple(
        R.drawable.perfume_test_1,
        "Test1",
        "are also"
    ),
    Triple(
        R.drawable.perfume_test_2,
        "Test2",
        "Hexadecimal"
    ),
    Triple(
        R.drawable.perfume_test_0,
        "Test0",
        "browsers."
    ),
    Triple(
        R.drawable.perfume_test_1,
        "Test1",
        "supported"
    ),
    Triple(
        R.drawable.perfume_test_2,
        "Test2",
        "in all browsers."
    )
)

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()
    val adPagerState = rememberPagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(color = background())
    ) {
        val screenWidth = LocalConfiguration.current.screenWidthDp.dp
        val calculatedHeight = (screenWidth / 16F) * 9F

        Box(modifier = Modifier.wrapContentSize()) {
            CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
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
                    modifier = Modifier.align(Alignment.Center),
                    text = "${adPagerState.currentPage + 1} / ${adPagerState.pageCount}",
                    fontColor = Color.White,
                    fontSize = 12.sp
                )
            }
        }

        APAppendedText(
            modifier = Modifier.padding(12.dp),
            annotatedString = buildAnnotatedString {
                withStyle(style = SpanStyle(color = mainTextColor())) {
                    append("지호")
                }

                withStyle(style = SpanStyle(color = subTextColor())) {
                    append(stringResource(id = R.string.perfumes_for_you))
                }
            },
            fontSize = 20.sp,
            fontType = FontType.Bold
        )

        CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
            LazyRow(
                modifier = Modifier
                    .wrapContentSize()
            ) {
                items(dummyPerfumesForYou) { perfume ->
                    Card(
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(136.dp)
                            .padding(horizontal = 12.dp),
                        elevation = 4.dp,
                        shape = RoundedCornerShape(12.dp),
                        backgroundColor = subBackground()
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Image(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(120.dp)
                                    .clip(shape = RoundedCornerShape(12.dp))
                                    .background(contentBackground()),
                                painter = painterResource(id = perfume.first),
                                contentDescription = "perfumeImage",
                                contentScale = ContentScale.FillBounds
                            )
                            APText(
                                modifier = Modifier.padding(horizontal = 8.dp),
                                text = perfume.second,
                                fontColor = mainTextColor(),
                                fontSize = 14.sp
                            )
                            APText(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 8.dp),
                                text = perfume.third,
                                fontColor = subTextColor(),
                                fontSize = 12.sp
                            )
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
    HomeScreen()
}