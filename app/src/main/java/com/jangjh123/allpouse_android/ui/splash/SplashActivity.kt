package com.jangjh123.allpouse_android.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.jangjh123.allpouse_android.ui.on_boarding.OnBoardingActivity
import com.jangjh123.allpouse_android.ui.theme.*
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
                brush = Brush.verticalGradient(
                    colors = listOf(
                        mainColor(),
                        mainGradient0(),
                        mainGradient1()
                    )
                )
            )
    )
}


@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
        SplashActivityContent()
    }
}