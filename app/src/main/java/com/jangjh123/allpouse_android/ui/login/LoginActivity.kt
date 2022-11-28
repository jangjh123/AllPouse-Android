package com.jangjh123.allpouse_android.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.ComposableFunctions.APText
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.cinzelExtraBold

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                LoginActivityContent(
                    onClickGoogleLogin = {

                    },
                    onClickKakaoLogin = {

                    }
                )
            }
        }
    }
}

@Composable
private fun LoginActivityContent(
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xdd000000))
                .alpha(1f),
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = "background",
            contentScale = ContentScale.Crop
        )

        Box(
            Modifier
                .fillMaxSize()
                .background(color = Color(0x77000000))
        )

        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .weight(0.6f)
                    .fillMaxWidth()
            ) {
                Column(
                    Modifier
                        .wrapContentSize()
                        .align(Center)
                ) {

                    Text(
                        text = stringResource(id = R.string.app),
                        fontFamily = cinzelExtraBold,
                        color = Color.White,
                        fontSize = 32.sp,
                        textAlign = TextAlign.Center
                    )
                    APText(
                        modifier = Modifier.align(CenterHorizontally),
                        text = stringResource(id = R.string.desc_app),
                        fontColor = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
            Column(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .weight(0.4f)
                    .fillMaxWidth()
                    .background(color = Color(0x55000000))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .align(Center)
                    ) {
                        APText(
                            modifier = Modifier.align(CenterHorizontally),
                            text = stringResource(id = R.string.social_login),
                            fontColor = Color.LightGray,
                            fontSize = 14.sp
                        )

                        LoginButton(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(top = 16.dp)
                                .clickable {
                                    onClickGoogleLogin()
                                },
                            borderColor = Color.White,
                            backgroundColor = Color.White,
                            iconId = R.drawable.google,
                            textId = R.string.google_login
                        )

                        LoginButton(
                            modifier = Modifier
                                .align(CenterHorizontally)
                                .padding(top = 10.dp)
                                .clickable {
                                    onClickKakaoLogin()
                                },
                            borderColor = Color(0xFFF9E000),
                            backgroundColor = Color(0xFFF9E000),
                            iconId = R.drawable.kakao,
                            textId = R.string.kakao_login
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun LoginButton(
    modifier: Modifier,
    borderColor: Color,
    backgroundColor: Color,
    iconId: Int,
    textId: Int,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(50))
            .border(
                width = 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .background(color = backgroundColor)
            .width(280.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(id = iconId),
            contentDescription = "loginPlatform",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 30.dp)
                .size(20.dp),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = stringResource(id = textId),
            fontSize = 15.sp,
            letterSpacing = (-0.5).sp,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(start = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
    }
}