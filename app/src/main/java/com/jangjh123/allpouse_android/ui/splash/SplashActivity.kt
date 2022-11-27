package com.jangjh123.allpouse_android.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.ComposableFunctions.APText
import com.jangjh123.allpouse_android.ui.on_boarding.OnBoardingActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : ComponentActivity() {
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                SplashActivityContent()
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
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF50312D),
                        Color(0xFF603A36),
                        Color(0xFF70443F),
                        Color(0xFF804E48),
                        Color(0xFF905851),
                        Color(0xFFA0625B),
                        Color(0xFFA0625B),
                        Color(0xFF905851),
                        Color(0xFF804E48),
                        Color(0xFF70443F),
                        Color(0xFF603A36),
                        Color(0xFF50312D)
                    )
                )
            )
    ) {
        Column(
            Modifier
                .wrapContentSize()
                .align(Center)
        ) {
            Text(
                modifier = Modifier.align(CenterHorizontally),
                text = stringResource(id = R.string.app),
                color = Color.White,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ),
                fontFamily = cinzelExtraBold,
                fontSize = 48.sp,

                )
            APText(
                modifier = Modifier
                    .align(CenterHorizontally),
                text = stringResource(id = R.string.desc_app),
                fontColor = Color.White,
                fontSize = 13.sp
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