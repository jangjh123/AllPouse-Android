package com.jangjh123.allpouse_android.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.main.MainActivity
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                val signUpBottomSheetState =
                    rememberModalBottomSheetState(
                        initialValue = ModalBottomSheetValue.Hidden,
                        skipHalfExpanded = true,
                        confirmStateChange = { false }
                    )
                val scope = rememberCoroutineScope()
                LoginActivityContent(
                    signUpBottomSheetState,
                    onClickGoogleLogin = {
                        scope.launch {
                            signUpBottomSheetState.show()
                        }
                    },
                    onClickKakaoLogin = {
                        scope.launch {
                            signUpBottomSheetState.show()
                        }
                    },
                    onClickStartButton = {
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    },
                    onClickClose = {
                        scope.launch {
                            signUpBottomSheetState.hide()
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LoginActivityContent(
    modalBottomSheetState: ModalBottomSheetState,
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit,
    onClickStartButton: () -> Unit,
    onClickClose: () -> Unit
) {
    val textFieldFocusManager = LocalFocusManager.current

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            SignUpModalBottomSheet(textFieldFocusManager, onClickStartButton = {
                onClickStartButton()
            },
                onClickClose = {
                    onClickClose()
                })
        },
        sheetElevation = 20.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        scrimColor = Color(0x88000000),
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                textFieldFocusManager.clearFocus()
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        textFieldFocusManager.clearFocus()
                    })
                },
        ) {
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
                        .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
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

        APText(
            modifier = Modifier
                .align(Center)
                .padding(start = 16.dp),
            text = stringResource(id = textId),
            fontSize = 14.sp,
            fontColor = Color.Black
        )
    }
}

@Composable
private fun SignUpModalBottomSheet(
    focusManager: FocusManager,
    onClickStartButton: () -> Unit,
    onClickClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(420.dp)
            .fillMaxWidth()
            .background(background())
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 20.dp)
                .fillMaxSize()
                .background(background())
        ) {
            APText(
                text = stringResource(id = R.string.nickName),
                fontType = FontType.Bold,
                fontSize = 20.sp
            )

            val nicknameState = remember { mutableStateOf("") }

            APTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                textFieldState = nicknameState,
                onValueChanged = { nicknameState.value = it },
                focusManager = focusManager,
            )

            APText(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = R.string.gender),
                fontType = FontType.Bold,
                fontSize = 20.sp
            )

            val genderState = remember { mutableStateOf(0) }
            Row(
                Modifier
                    .wrapContentSize()
                    .padding(top = 8.dp)
            ) {
                GenderButton(
                    modifier = Modifier,
                    gender = stringResource(id = R.string.male),
                    onClickGenderButton = {
                        genderState.value = 1
                    },
                    genderState
                )
                GenderButton(
                    modifier = Modifier.padding(start = 20.dp),
                    gender = stringResource(id = R.string.female),
                    onClickGenderButton = {
                        genderState.value = 2
                    },
                    genderState
                )
            }

            APText(
                modifier = Modifier.padding(top = 20.dp),
                text = stringResource(id = R.string.age),
                fontType = FontType.Bold,
                fontSize = 20.sp
            )

            val ageState = remember { mutableStateOf("") }
            APTextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                textFieldState = ageState,
                onValueChanged = { ageState.value = it },
                focusManager = focusManager,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            val buttonAlphaState = animateFloatAsState(
                targetValue =
                if (nicknameState.value.isNotEmpty() && genderState.value != 0 && ageState.value.isNotEmpty()) {
                    1f
                } else {
                    0.3f
                }
            )

            GradientButton(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .alpha(buttonAlphaState.value)
                    .fillMaxWidth()
                    .height(60.dp),
                text = stringResource(id = R.string.start),
                fontSize = 18.sp,
                onClickButton = {
                    onClickStartButton()
                }
            )
        }
        CloseIcon(modifier = Modifier
            .padding(top = 24.dp, end = 20.dp)
            .size(24.dp)
            .align(Alignment.TopEnd)
            .clickable {
                onClickClose()
            })
    }
}

@Composable
private fun GenderButton(
    modifier: Modifier,
    gender: String,
    onClickGenderButton: () -> Unit,
    genderState: MutableState<Int>
) {
    val buttonColorState =
        animateColorAsState(
            targetValue = if (genderState.value == 1 && gender == stringResource(id = R.string.male)) {
                mainColor()
            } else if (genderState.value == 2 && gender == stringResource(id = R.string.female)) {
                mainColor()
            } else {
                subBackground()
            }
        )

    val textColorState =
        animateColorAsState(
            targetValue = if (genderState.value == 1 && gender == stringResource(id = R.string.male)) {
                Color.White
            } else if (genderState.value == 2 && gender == stringResource(id = R.string.female)) {
                Color.White
            } else {
                mainTextColor()
            }
        )
    Box(
        modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClickGenderButton() }
            .width(120.dp)
            .height(40.dp)
            .background(color = buttonColorState.value)
    ) {
        APText(
            modifier = Modifier.align(Center),
            text = gender,
            fontColor = if (!isSystemInDarkTheme()) {
                textColorState.value
            } else {
                mainTextColor()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
//        SignUpModalBottomSheet()
    }
}