package com.jangjh123.allpouse_android.ui.screen.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.lifecycleScope
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.NoticeDialog
import com.jangjh123.allpouse_android.ui.screen.main.MainActivity
import com.jangjh123.allpouse_android.ui.screen.on_boarding.OnBoardingActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

var SCREEN_HEIGHT_DP = 705.dp
var SCREEN_WIDTH_DP = 360.dp

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    private val viewModel: SplashViewModel by viewModels()
    private val noticeDialogState = mutableStateOf(false)

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // todo : onNewVersionExist

        if (!isNetworkEnabled()) {
            noticeDialogState.value = true
        } else {
            lifecycleScope.launch {
                viewModel.signInState.collectLatest { state ->
                    when (state) {
                        is SignInState.Loading -> {
                            lifecycleScope.launch {
                                delay(1000L)
                                viewModel.signIn()
                            }
                        }
                        is SignInState.OnSuccess -> {
                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    MainActivity::class.java
                                )
                            )
                        }
                        is SignInState.OnFailure -> {
                            startActivity(
                                Intent(
                                    this@SplashActivity,
                                    OnBoardingActivity::class.java
                                )
                            )
                        }
                    }
                }
            }
        }



        setContent {
            AllPouseAndroidTheme {
                SplashActivityContent()
                SCREEN_WIDTH_DP = LocalConfiguration.current.screenWidthDp.dp
                SCREEN_HEIGHT_DP = LocalConfiguration.current.screenHeightDp.dp

                if (noticeDialogState.value) {
                    Dialog(
                        onDismissRequest = {
                            noticeDialogState.value = false
                        },
                        properties = DialogProperties(
                            dismissOnBackPress = false,
                            dismissOnClickOutside = false
                        )
                    ) {
                        NoticeDialog(
                            text = stringResource(
                                id = R.string.network_disabled
                            ),
                            buttonText = stringResource(
                                id = R.string.quit
                            )
                        ) {
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun isNetworkEnabled(): Boolean {
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val active = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            active.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            active.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
            else -> false
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