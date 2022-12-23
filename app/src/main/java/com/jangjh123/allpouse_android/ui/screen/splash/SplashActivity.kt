package com.jangjh123.allpouse_android.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.screen.on_boarding.OnBoardingActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

var SCREEN_HEIGHT_DP = 705.dp
var SCREEN_WIDTH_DP = 360.dp

class SplashActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                SplashActivityContent()
                SCREEN_WIDTH_DP = LocalConfiguration.current.screenWidthDp.dp
                SCREEN_HEIGHT_DP = LocalConfiguration.current.screenHeightDp.dp
                lifecycleScope.launch {
                    delay(2000L)
                    startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                }
            }
        }
    }
}

@Composable
private fun SplashActivityContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color.Black
            )
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Center)
        ) {
            Image(
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally),
                painter = painterResource(
                    id = R.drawable.main_icon
                ),
                contentDescription = "mainIcon",
                contentScale = ContentScale.FillBounds
            )

            Text(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = stringResource(
                    id = R.string.app
                ),
                color = Color.White,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    )
                ),
                fontFamily = cinzelExtraBold,
                fontSize = 24.sp,

                )
            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = stringResource(
                    id = R.string.desc_app
                ),
                fontColor = Color.White,
                fontSize = 12.sp
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
        SplashActivityContent()
    }
}