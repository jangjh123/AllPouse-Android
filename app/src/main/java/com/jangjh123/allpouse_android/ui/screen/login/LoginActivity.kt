package com.jangjh123.allpouse_android.ui.screen.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
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
import com.jangjh123.allpouse_android.ui.screen.image_crop.ImageCropActivity
import com.jangjh123.allpouse_android.ui.screen.login.Gender.*
import com.jangjh123.allpouse_android.ui.screen.main.MainActivity
import com.jangjh123.allpouse_android.ui.theme.*
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startActivityForProfileImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {

                }
            }
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
                    onClickProfileImage = {
                        startActivityForProfileImage.launch(
                            Intent(
                                this@LoginActivity,
                                ImageCropActivity::class.java
                            )
                        )
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
    onClickProfileImage: () -> Unit,
    onClickStartButton: () -> Unit,
    onClickClose: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background())
            ) {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .fillMaxWidth()
                        .background(background())
                ) {

                    Image(
                        modifier = Modifier
                            .padding(
                                all = 24.dp
                            )
                            .clip(
                                shape = CircleShape
                            )
                            .size(100.dp)
                            .background(
                                color = contentBackground()
                            )
                            .align(CenterHorizontally)
                            .clickable {
                                onClickProfileImage()
                            },
                        painter = painterResource(
                            id = R.drawable.main_icon
                        ),
                        contentDescription = "profileImage",
                        contentScale = ContentScale.FillBounds
                    )

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

                    val genderState = remember { mutableStateOf<Gender>(None) }
                    Row(
                        Modifier
                            .wrapContentSize()
                            .padding(top = 8.dp)
                    ) {
                        GenderButton(
                            modifier = Modifier,
                            gender = stringResource(id = R.string.male),
                            onClickGenderButton = {
                                genderState.value = Man
                            },
                            genderState
                        )
                        GenderButton(
                            modifier = Modifier.padding(start = 20.dp),
                            gender = stringResource(id = R.string.female),
                            onClickGenderButton = {
                                genderState.value = Woman
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
                        if (nicknameState.value.isNotEmpty() && genderState.value != None && ageState.value.isNotEmpty()) {
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
                    .clickableWithoutRipple {
                        onClickClose()
                    })
            }
        },
        sheetElevation = 20.dp,
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        scrimColor = darkFilter(),
        modifier = Modifier.addFocusCleaner(focusManager)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = darkFilter())
                    .alpha(1f),
                painter = painterResource(id = R.drawable.bg_login),
                contentDescription = "background",
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = darkFilter())
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
                        .background(color = darkFilter())
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

sealed class Gender {
    object None : Gender()
    object Man : Gender()
    object Woman : Gender()
}

@Composable
private fun GenderButton(
    modifier: Modifier,
    gender: String,
    onClickGenderButton: () -> Unit,
    genderState: MutableState<Gender>
) {
    val buttonColorState =
        animateColorAsState(
            targetValue = if (genderState.value == Man && gender == stringResource(id = R.string.male)) {
                mainColor()
            } else if (genderState.value == Woman && gender == stringResource(id = R.string.female)) {
                mainColor()
            } else {
                subBackground()
            }
        )

    val textColorState =
        animateColorAsState(
            targetValue = if (genderState.value == Man && gender == stringResource(id = R.string.male)) {
                Color.White
            } else if (genderState.value == Woman && gender == stringResource(id = R.string.female)) {
                Color.White
            } else {
                mainTextColor()
            }
        )
    Box(
        modifier
            .clip(RoundedCornerShape(8.dp))
            .clickableWithoutRipple { onClickGenderButton() }
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