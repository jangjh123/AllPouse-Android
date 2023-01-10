package com.jangjh123.allpouse_android.ui.screen.login

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.login.image_crop.ImageCropActivity
import com.jangjh123.allpouse_android.ui.theme.*
import com.jangjh123.allpouse_android.util.addFocusCleaner
import com.jangjh123.allpouse_android.util.clickableWithoutRipple
import com.jangjh123.allpouse_android.util.getImageBitmapFromUrl
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.Gender
import com.kakao.sdk.user.model.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@AndroidEntryPoint
class LoginActivity : ComponentActivity() {
    private val viewModel: LoginViewModel by viewModels()

    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>
    private lateinit var googleAccountState: MutableStateFlow<GoogleSignInAccount?>
    private lateinit var profileImageLauncher: ActivityResultLauncher<Intent>

    private val modalBottomSheetState = mutableStateOf(ModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        animationSpec = SwipeableDefaults.AnimationSpec,
        isSkipHalfExpanded = true,
        confirmStateChange = { false }
    ))
    private val imageState = mutableStateOf(ImageBitmap(1, 1))
    private val nicknameState = mutableStateOf("")
    private val ageState = mutableStateOf("")
    private val genderState = mutableStateOf<UserGender>(UserGender.None)

    private var loginType = ""
    private var socialId = ""

    private val socialLoginErrorDialogState = mutableStateOf(false)
    private val selectImageSourceDialogState = mutableStateOf(false)
    private val errorDialogState = mutableStateOf(false)
    private val errorDialogTextState = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false) // for WindowInsets

        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { intent ->
                if (intent.resultCode == RESULT_OK) {
                    GoogleSignIn.getSignedInAccountFromIntent(intent.data)
                        .getResult(ApiException::class.java).let { account ->
                            googleAccountState.value = account
                        }
                }
            }

        profileImageLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    imageState.value =
                        (result.data?.extras?.get("croppedImage") as Bitmap).asImageBitmap()
                }
            }

        setContent {
            AllPouseAndroidTheme {
                LoginActivityContent(
                    modalBottomSheetState = modalBottomSheetState,
                    imageState = imageState,
                    nicknameState = nicknameState,
                    ageState = ageState,
                    genderState = genderState,
                    errorDialogState = errorDialogState,
                    errorDialogTextState = errorDialogTextState,
                    onClickGoogleLogin = {
                        getGoogleUserInfo()
                    },
                    onClickKakaoLogin = {
                        getKakaoUserInfo()
                    },
                    onClickImageSource = {
                        selectImageSourceDialogState.value = true
                    },
                    onClickSignUp = {
                        viewModel.signUp(
                            socialId = socialId,
                            userName = nicknameState.value,
                            permission = "ROLE_USER",
                            age = ageState.value,
                            gender = when (genderState.value) {
                                is UserGender.Man -> "Man"
                                else -> "Woman"
                            },
                            loginType = loginType,
                        )
                    }
                )

                viewModel.signUpState.collectAsState().also { state ->
                    when (state.value) {
                        is UiState.OnLoading -> {
                            if ((state.value as UiState.OnLoading).isLoadingShown == true) {
                                LoadingDialog()
                            }
                        }
                        is UiState.OnSuccess -> {

                        }
                        is UiState.OnFailure -> {
                            socialLoginErrorDialogState.value = true
                        }
                    }
                }

                SelectImageSourceDialog(
                    state = selectImageSourceDialogState,
                    onClickCamera = {
                        profileImageLauncher.launch(
                            Intent(
                                this@LoginActivity, ImageCropActivity::class.java
                            ).apply {
                                putExtra("imageSource", "camera")
                            }
                        )
                    },
                    onClickGallery = {
                        profileImageLauncher.launch(
                            Intent(
                                this@LoginActivity,
                                ImageCropActivity::class.java
                            ).apply {
                                putExtra("imageSource", "gallery")
                            }
                        )
                    }
                )

                NoticeDialog(
                    state = errorDialogState,
                    text = errorDialogTextState.value
                )

                NoticeDialog(
                    state = socialLoginErrorDialogState,
                    text = stringResource(
                        id = R.string.social_login_error
                    )
                )
            }
        }
    }

    private fun getGoogleUserInfo() {
        loginType = "google"
        googleAccountState = MutableStateFlow(null)
        googleSignInLauncher.launch(
            GoogleSignIn.getClient(
                this@LoginActivity,
                GoogleSignInOptions
                    .Builder(
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    )
                    .requestEmail()
                    .build()
            ).signInIntent
        )

        lifecycleScope.launch {
            googleAccountState.collectLatest { googleAccount ->
                if (googleAccount != null) {
                    socialId =
                        googleAccount.id.toString()
                    if (googleAccount.photoUrl != null) {
                        getImageBitmapFromUrl(
                            context = this@LoginActivity,
                            url = googleAccount.photoUrl.toString(),
                            onSuccess = { result ->
                                imageState.value = result
                            }
                        )
                    }
                }
            }
        }
    }

    private fun getKakaoUserInfo() {
        loginType = "kakaotalk"
        lifecycleScope.launch {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(
                    this@LoginActivity
                )
            ) {
                UserApiClient.instance.loginWithKakaoTalk(
                    this@LoginActivity
                ) { token, _ ->
                    if (token != null) {
                        UserApiClient.instance.me { user, _ ->
                            if (user != null) {
                                setUserInfoWithKakao(user)
                            }
                        }
                    } else {
                        socialLoginErrorDialogState.value = true
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(
                    this@LoginActivity
                ) { token: OAuthToken?, _ ->
                    if (token != null) {
                        UserApiClient.instance.me { user, _ ->
                            if (user != null) {
                                setUserInfoWithKakao(user)
                            }
                        }
                    } else {
                        socialLoginErrorDialogState.value = true
                    }
                }
            }
        }
    }

    private fun setUserInfoWithKakao(
        user: User
    ) {
        socialId = user.id.toString()
        getImageBitmapFromUrl(
            context = this@LoginActivity,
            url = user.kakaoAccount?.profile?.profileImageUrl.toString(),
            onSuccess = { kakaoProfileImage ->
                imageState.value = kakaoProfileImage
            }
        )
        nicknameState.value =
            user.kakaoAccount?.profile?.nickname.toString()
        genderState.value = when (user.kakaoAccount?.gender) {
            Gender.MALE -> {
                UserGender.Man
            }
            Gender.FEMALE -> {
                UserGender.Woman
            }
            else -> {
                UserGender.None
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun LoginActivityContent(
    modalBottomSheetState: MutableState<ModalBottomSheetState>,
    imageState: MutableState<ImageBitmap>,
    nicknameState: MutableState<String>,
    ageState: MutableState<String>,
    genderState: MutableState<UserGender>,
    errorDialogState: MutableState<Boolean>,
    errorDialogTextState: MutableState<String>,
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit,
    onClickImageSource: () -> Unit,
    onClickSignUp: () -> Unit
) {
    val composeScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val isDigit: (String) -> Boolean = { input: String ->
        var isPossible = true
        input.forEach { number ->
            if (!number.isDigit()) {
                isPossible = false
            }
        }
        isPossible
    }

    ModalBottomSheetLayout(
        sheetState = modalBottomSheetState.value,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(background())
                    .windowInsetsPadding(
                        insets = WindowInsets.systemBars.only(
                            sides = WindowInsetsSides.Vertical
                        )
                    )
                    .imePadding()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = 20.dp,
                        )
                        .padding(
                            top = 20.dp
                        )
                        .fillMaxWidth()
                ) {

                    Box(
                        modifier = Modifier
                            .padding(
                                all = 24.dp
                            )
                            .size(100.dp)
                            .clip(
                                shape = CircleShape
                            )
                            .background(
                                color = contentBackground()
                            )
                            .align(CenterHorizontally)
                    ) {
                        APText(
                            modifier = Modifier
                                .align(Center),
                            text = stringResource(
                                id = R.string.profile_image
                            ),
                            fontSize = 10.sp
                        )

                        Image(
                            modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .size(100.dp)
                                .clickable {
                                    onClickImageSource()
                                },
                            bitmap = imageState.value,
                            contentDescription = "profileImage",
                            contentScale = ContentScale.FillBounds
                        )
                    }

                    APText(
                        text = stringResource(
                            id = R.string.nickName
                        ),
                        fontType = FontType.Bold,
                        fontSize = 20.sp
                    )

                    APTextField(
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            )
                            .fillMaxWidth(),
                        textFieldState = nicknameState,
                        onValueChanged = { nicknameState.value = it },
                        focusManager = focusManager,
                    )

                    APText(
                        modifier = Modifier
                            .padding(
                                top = 20.dp
                            ),
                        text = stringResource(
                            id = R.string.birth
                        ),
                        fontType = FontType.Bold,
                        fontSize = 20.sp
                    )

                    APTextField(
                        modifier = Modifier
                            .padding(
                                top = 8.dp
                            )
                            .fillMaxWidth(),
                        textFieldState = ageState,
                        onValueChanged = { ageState.value = it },
                        placeholder = {
                            APText(
                                text = "ex) 20040101",
                                fontColor = subTextColor(),
                                fontSize = 12.sp
                            )
                        },
                        focusManager = focusManager,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Number
                        )
                    )

                    val buttonAlphaState = animateFloatAsState(
                        targetValue =
                        if (nicknameState.value.isNotEmpty()
                            && genderState.value != UserGender.None
                            && ageState.value.isNotEmpty()
                        ) 1f
                        else 0.3f
                    )

                    APText(
                        modifier = Modifier
                            .padding(
                                top = 20.dp
                            ),
                        text = stringResource(
                            id = R.string.gender
                        ),
                        fontType = FontType.Bold,
                        fontSize = 20.sp
                    )

                    Row(
                        Modifier
                            .wrapContentSize()
                            .padding(
                                top = 8.dp
                            )
                    ) {
                        GenderButton(
                            modifier = Modifier,
                            gender = stringResource(
                                id = R.string.male
                            ),
                            onClickGenderButton = {
                                genderState.value = UserGender.Man
                            },
                            genderState
                        )
                        GenderButton(
                            modifier = Modifier.padding(
                                start = 20.dp
                            ),
                            gender = stringResource(
                                id = R.string.female
                            ),
                            onClickGenderButton = {
                                genderState.value = UserGender.Woman
                            },
                            genderState
                        )
                    }

                    GradientButton(
                        modifier = Modifier
                            .padding(
                                vertical = 20.dp
                            )
                            .alpha(buttonAlphaState.value)
                            .fillMaxWidth()
                            .height(60.dp),
                        text = stringResource(
                            id = R.string.start
                        ),
                        fontSize = 18.sp,
                        onClickButton = {
                            if (buttonAlphaState.value != 0.3f) {
                                if (nicknameState.value.isEmpty()) {
                                    errorDialogState.value = true
                                    errorDialogTextState.value = "닉네임을 입력해 주세요."
                                } else if (genderState.value == UserGender.None) {
                                    errorDialogState.value = true
                                    errorDialogTextState.value = "성별을 선택해 주세요."
                                } else if (ageState.value.isEmpty()
                                    || ageState.value.length != 8
                                    || !isDigit(ageState.value)
                                ) {
                                    errorDialogState.value = true
                                    errorDialogTextState.value = "생년월일을 올바르게 입력해 주세요."
                                } else {
                                    onClickSignUp()
                                }
                            }
                        }
                    )
                }

                CloseIcon(
                    modifier = Modifier
                        .padding(
                            top = 24.dp,
                            end = 20.dp
                        )
                        .size(24.dp)
                        .align(Alignment.TopEnd)
                        .clickableWithoutRipple {
                            composeScope.launch {
                                modalBottomSheetState.value.hide()
                            }
                        })
            }
        },
        sheetElevation = 20.dp,
        sheetShape = RoundedCornerShape(
            topStart = 24.dp,
            topEnd = 24.dp
        ),
        scrimColor = darkFilter(),
        modifier = Modifier.addFocusCleaner(focusManager)
    ) {
        val scope = rememberCoroutineScope()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .addFocusCleaner(focusManager),
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = darkFilter()
                    )
                    .alpha(1f),
                painter = painterResource(
                    id = R.drawable.bg_login
                ),
                contentDescription = "background",
                contentScale = ContentScale.Crop
            )

            Box(
                Modifier
                    .fillMaxSize()
                    .background(
                        color = darkFilter()
                    )
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
                            text = stringResource(
                                id = R.string.app
                            ),
                            fontFamily = cinzelExtraBold,
                            color = Color.White,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                        APText(
                            modifier = Modifier.align(CenterHorizontally),
                            text = stringResource(
                                id = R.string.desc_app
                            ),
                            fontColor = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(
                                topStart = 24.dp,
                                topEnd = 24.dp
                            )
                        )
                        .weight(0.4f)
                        .fillMaxWidth()
                        .background(
                            color = darkFilter()
                        )
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
                                text = stringResource(
                                    id = R.string.social_login
                                ),
                                fontColor = Color.LightGray,
                                fontSize = 14.sp
                            )

                            LoginButton(
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .padding(
                                        top = 16.dp
                                    )
                                    .clickable {
                                        onClickGoogleLogin()
                                        scope.launch {
                                            modalBottomSheetState.value.show()
                                        }
                                    },
                                borderColor = Color.White,
                                backgroundColor = Color.White,
                                iconId = R.drawable.google,
                                textId = R.string.google_login
                            )

                            LoginButton(
                                modifier = Modifier
                                    .align(CenterHorizontally)
                                    .padding(
                                        top = 10.dp
                                    )
                                    .clickable {
                                        onClickKakaoLogin()
                                        scope.launch {
                                            modalBottomSheetState.value.show()
                                        }
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
            .clip(
                shape = RoundedCornerShape(50)
            )
            .border(
                width = 0.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(50)
            )
            .background(
                color = backgroundColor
            )
            .width(280.dp)
            .height(50.dp)
    ) {
        Image(
            painter = painterResource(
                id = iconId
            ),
            contentDescription = "loginPlatform",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(
                    start = 30.dp
                )
                .size(20.dp),
            contentScale = ContentScale.FillBounds
        )

        APText(
            modifier = Modifier
                .align(Center)
                .padding(
                    start = 16.dp
                ),
            text = stringResource(
                id = textId
            ),
            fontSize = 14.sp,
            fontColor = Color.Black
        )
    }
}

@Composable
private fun GenderButton(
    modifier: Modifier,
    gender: String,
    onClickGenderButton: () -> Unit,
    genderState: MutableState<UserGender>,
) {
    val buttonColorState =
        animateColorAsState(
            targetValue =
            if (genderState.value == UserGender.Man && gender == stringResource(
                    id = R.string.male
                )
            ) mainColor()
            else if (genderState.value == UserGender.Woman && gender == stringResource(
                    id = R.string.female
                )
            ) mainColor()
            else subBackground()

        )

    val textColorState =
        animateColorAsState(
            targetValue =
            if (genderState.value == UserGender.Man && gender == stringResource(
                    id = R.string.male
                )
            )
                Color.White
            else if (genderState.value == UserGender.Woman && gender == stringResource(
                    id = R.string.female
                )
            )
                Color.White
            else mainTextColor()

        )
    Box(
        modifier = modifier
            .clip(
                shape = RoundedCornerShape(8.dp)
            )
            .clickableWithoutRipple { onClickGenderButton() }
            .width(120.dp)
            .height(40.dp)
            .background(
                color = buttonColorState.value
            )
    ) {
        APText(
            modifier = Modifier.align(Center),
            text = gender,
            fontColor =
            if (!isSystemInDarkTheme()) textColorState.value
            else mainTextColor()

        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    AllPouseAndroidTheme {
    }
}

sealed class UserGender {
    object None : UserGender()
    object Man : UserGender()
    object Woman : UserGender()
}