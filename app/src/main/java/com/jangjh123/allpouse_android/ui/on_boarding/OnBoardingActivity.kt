package com.jangjh123.allpouse_android.ui.on_boarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.BottomEnd
import androidx.compose.ui.Alignment.Companion.BottomStart
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
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
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.ComposableFunctions.APText
import com.jangjh123.allpouse_android.ui.component.FontType
import com.jangjh123.allpouse_android.ui.component.HorizontalScrollConsumer
import com.jangjh123.allpouse_android.ui.login.LoginActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import com.jangjh123.allpouse_android.ui.theme.mainColor
import kotlinx.coroutines.launch

class OnBoardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                OnBoardingActivityContent(onClickStart = {
                    startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
                })
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
private fun OnBoardingActivityContent(onClickStart: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val screens = listOf(
        Triple(
            painterResource(id = R.drawable.splash_bg_0),
            stringResource(R.string.center_text_page_0),
            stringResource(R.string.desc_page_0)
        ),
        Triple(
            painterResource(id = R.drawable.splash_bg_1),
            stringResource(R.string.center_text_page_1),
            stringResource(R.string.desc_page_1)
        ),
        Triple(
            painterResource(id = R.drawable.splash_bg_2),
            stringResource(R.string.center_text_page_2),
            stringResource(R.string.desc_page_2)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalOverscrollConfiguration.provides(null)) {
            HorizontalPager(
                state = pagerState,
                count = 3,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(HorizontalScrollConsumer)
            ) { page ->
                val item = screens[page]
                Box(Modifier.fillMaxSize()) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = item.first,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                    )

                    Column(Modifier.fillMaxSize()) {
                        Box(modifier = Modifier.weight(0.6f))
                        Box(
                            modifier = Modifier
                                .weight(0.4f)
                                .fillMaxWidth()
                                .background(color = Color(0x99000000))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(20.dp)
                            ) {
                                APText(
                                    modifier = Modifier.align(CenterHorizontally),
                                    text = item.second,
                                    fontColor = mainColor(),
                                    fontSize = 24.sp,
                                    fontType = FontType.Bold,
                                )

                                APText(
                                    modifier = Modifier
                                        .padding(top = 30.dp)
                                        .align(CenterHorizontally),
                                    text = item.third,
                                    fontSize = 14.sp,
                                    fontColor = Color.LightGray,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = mainColor(), inactiveColor = Color.LightGray,
                modifier = Modifier
                    .align(BottomStart)
                    .padding(20.dp)
            )

            Text(
                modifier = Modifier
                    .align(BottomCenter)
                    .padding(bottom = 15.dp),
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                text = stringResource(id = R.string.app),
                color = Color.White,
                fontSize = 12.sp,
                fontFamily = cinzelExtraBold
            )

            APText(
                modifier = Modifier
                    .align(BottomEnd)
                    .padding(end = 20.dp, bottom = 13.dp)
                    .clickable {
                        if (pagerState.currentPage == 2) {
                            onClickStart()
                        } else {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                text = if (pagerState.currentPage == 2) stringResource(id = R.string.start) else stringResource(
                    id = R.string.next
                ),
                fontColor = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
    }
}