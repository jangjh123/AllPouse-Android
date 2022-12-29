package com.jangjh123.allpouse_android.ui.screen.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.FontType
import com.jangjh123.allpouse_android.util.clickableWithoutRipple
import com.jangjh123.allpouse_android.ui.screen.login.LoginActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import com.jangjh123.allpouse_android.ui.theme.mainColor
import kotlinx.coroutines.launch

class OnBoardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                OnBoardingActivityContent(
                    onClickStart = {
                        startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
                    })
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun OnBoardingActivityContent(
    onClickStart: () -> Unit
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val screens = listOf(
        Triple(
            painterResource(
                id = R.drawable.on_boarding_bg_0
            ),
            stringResource(R.string.center_text_page_0),
            stringResource(R.string.desc_page_0)
        ),
        Triple(
            painterResource(
                id = R.drawable.on_boarding_bg_1
            ),
            stringResource(R.string.center_text_page_1),
            stringResource(R.string.desc_page_1)
        ),
        Triple(
            painterResource(
                id = R.drawable.on_boarding_bg_2
            ),
            stringResource(R.string.center_text_page_2),
            stringResource(R.string.desc_page_2)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            count = 3,
            modifier = Modifier
                .fillMaxSize()
        ) { page ->
            val item = screens[page]
            Box(Modifier.fillMaxSize()) {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = item.first,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color(0x88000000)
                        )
                )

                Column(Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .weight(0.5f)
                    )
                    Box(
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxWidth()
                            .background(
                                color = Color(0xAA000000)
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                        ) {
                            APText(
                                modifier = Modifier
                                    .align(CenterHorizontally),
                                text = item.second,
                                fontColor = mainColor(),
                                fontSize = 28.sp,
                                fontType = FontType.Bold,
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 30.dp
                                    )
                                    .align(CenterHorizontally),
                                text = item.third,
                                fontSize = 16.sp,
                                fontColor = Color.LightGray
                            )
                        }
                    }
                }
            }
        }

        CustomHorizontalPagerIndicator(
            modifier = Modifier
                .padding(20.dp)
                .align(BottomStart),
            count = pagerState.pageCount,
            state = pagerState,
            focusedColor = mainColor(),
            unfocusedColor = Color.LightGray
        )

        Text(
            modifier = Modifier
                .align(BottomCenter)
                .padding(
                    bottom = 15.dp
                ),
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
            ),
            text = stringResource(
                id = R.string.app
            ),
            color = Color.White,
            fontSize = 12.sp,
            fontFamily = cinzelExtraBold
        )

        APText(
            modifier = Modifier
                .align(BottomEnd)
                .padding(
                    end = 20.dp,
                    bottom = 13.dp
                )
                .clickableWithoutRipple {
                    if (pagerState.currentPage == 2) {
                        onClickStart()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
            text = if (pagerState.currentPage == 2) stringResource(
                id = R.string.start
            ) else stringResource(
                id = R.string.next
            ),
            fontColor = Color.White
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomHorizontalPagerIndicator(
    modifier: Modifier,
    count: Int,
    state: PagerState,
    focusedColor: Color,
    unfocusedColor: Color,
) {
    Row(
        modifier = modifier
    ) {
        for (i in 0 until count) {
            IndicatorSymbol(
                currentItemIndex = state.currentPage,
                symbolIndex = i,
                focusedColor = focusedColor,
                unfocusedColor = unfocusedColor
            )
        }
    }
}

@Composable
fun IndicatorSymbol(
    currentItemIndex: Int,
    symbolIndex: Int,
    focusedColor: Color,
    unfocusedColor: Color,
) {
    val widthState =
        animateDpAsState(
            targetValue =
            if (currentItemIndex == symbolIndex) 16.dp
            else 8.dp
        )
    val colorState =
        animateColorAsState(
            targetValue =
            if (currentItemIndex == symbolIndex) focusedColor
            else unfocusedColor
        )

    Box(
        modifier = Modifier
            .width(24.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(255.dp)
                )
                .width(widthState.value)
                .height(8.dp)
                .background(
                    color = colorState.value
                )
                .align(Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
    }
}