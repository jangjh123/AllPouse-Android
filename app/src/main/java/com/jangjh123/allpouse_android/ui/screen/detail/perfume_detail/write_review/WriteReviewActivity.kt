package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.write_review

import android.Manifest.permission.CAMERA
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.*
import com.jangjh123.allpouse_android.ui.screen.base.GetImageBaseActivity
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.background
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WriteReviewActivity : GetImageBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val perfumeId = intent.getIntExtra("perfumeId", 0)
            val perfumeName = intent.getStringExtra("perfumeName")

            AllPouseAndroidTheme {
                val scrollState = rememberScrollState()
                val focusManager = LocalFocusManager.current
                val titleTextFieldState = remember { mutableStateOf("") }
                val image0State = remember { mutableStateOf<ImageBitmap?>(null) }
                val image1State = remember { mutableStateOf<ImageBitmap?>(null) }
                val image2State = remember { mutableStateOf<ImageBitmap?>(null) }
                val bodyTextFieldState = remember { mutableStateOf("") }
                val selectImageSourceDialogState = remember { mutableStateOf(false) }

                ActivityFrame(this@WriteReviewActivity) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                color = background()
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(
                                    state = scrollState
                                )
                                .windowInsetsPadding(
                                    insets = WindowInsets.systemBars.only(
                                        sides = WindowInsetsSides.Vertical
                                    )
                                )
                                .imePadding()
                        ) {
                            Spacer(
                                modifier = Modifier
                                    .height(50.dp)
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 12.dp,
                                        start = 12.dp
                                    ),
                                text = stringResource(
                                    id = R.string.review_title
                                ),
                                fontSize = 18.sp,
                                fontType = FontType.Bold
                            )

                            APTextField(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth()
                                    .height(60.dp),
                                textFieldState = titleTextFieldState,
                                onValueChanged = {
                                    titleTextFieldState.value = it
                                },
                                focusManager = focusManager
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 12.dp,
                                        start = 12.dp
                                    ),
                                text = stringResource(
                                    id = R.string.review_image
                                ),
                                fontSize = 18.sp,
                                fontType = FontType.Bold
                            )

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                            ) {
                                ReviewImagePlaceHolder(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .weight(1f),
                                    bitmap = image0State,
                                ) {
                                    imageState = image0State
                                    selectImageSourceDialogState.value = true
                                }

                                ReviewImagePlaceHolder(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .weight(1f),
                                    bitmap = image1State,
                                ) {
                                    imageState = image1State
                                    selectImageSourceDialogState.value = true
                                }

                                ReviewImagePlaceHolder(
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .weight(1f),
                                    bitmap = image2State,
                                ) {
                                    imageState = image2State
                                    selectImageSourceDialogState.value = true
                                }
                            }

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 12.dp,
                                        start = 12.dp
                                    ),
                                text = stringResource(
                                    id = R.string.review_body
                                ),
                                fontSize = 18.sp,
                                fontType = FontType.Bold
                            )

                            APTextField(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth()
                                    .heightIn(
                                        min = 200.dp
                                    ),
                                textFieldState = bodyTextFieldState,
                                onValueChanged = { bodyTextFieldState.value = it },
                                focusManager = focusManager,
                                singleLine = false
                            )

                            // 내용 입력에 대한 자동 스크롤 처리
                            LaunchedEffect(scrollState.maxValue) {
                                if (bodyTextFieldState.value.isNotEmpty()) {
                                    scrollState.scrollBy(60f)
                                }
                            }

                            GradientButton(
                                modifier = Modifier
                                    .padding(24.dp)
                                    .fillMaxWidth()
                                    .height(50.dp),
                                text = stringResource(
                                    id = R.string.complete_writing_review
                                ),
                                fontSize = 16.sp
                            ) {

                            }
                        }

                        Box(
                            modifier = Modifier
                                .shadow(4.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(
                                    color = background()
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {
                                APText(
                                    text = perfumeName ?: "",
                                    fontType = FontType.Bold,
                                    fontSize = 18.sp
                                )

                                APText(
                                    modifier = Modifier
                                        .padding(
                                            start = 12.dp
                                        ),
                                    text = stringResource(
                                        id = R.string.write_review
                                    ),
                                    fontType = FontType.Bold,
                                    fontSize = 18.sp,
                                    fontColor = subTextColor()
                                )
                            }
                        }

                        if (selectImageSourceDialogState.value) {
                            Dialog(
                                onDismissRequest = {
                                    selectImageSourceDialogState.value = false
                                }) {
                                SelectImageSourceDialog(
                                    onClickCamera = {
                                        cameraPermission.launch(CAMERA)
                                        selectImageSourceDialogState.value = false
                                    },
                                    onClickGallery = {
                                        galleryLauncher.launch(PickVisualMediaRequest())
                                        selectImageSourceDialogState.value = false
                                    }
                                )
                            }
                        }
                    }
                }
            }

            needCameraPermissionDialogState = remember { mutableStateOf(false) }
            needGalleryPermissionDialogState = remember { mutableStateOf(false) }
            imageLoadErrorDialogState = remember { mutableStateOf(false) }

            if (needCameraPermissionDialogState.value) {
                Dialog(
                    onDismissRequest = {
                        needCameraPermissionDialogState.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                ) {
                    NoticeDialog(
                        text = stringResource(
                            id = R.string.need_permission, "카메라"
                        )
                    ) {
                        needCameraPermissionDialogState.value = false
                    }
                }
            }

            if (needGalleryPermissionDialogState.value) {
                Dialog(
                    onDismissRequest = {
                        needGalleryPermissionDialogState.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                ) {
                    NoticeDialog(
                        text = stringResource(
                            id = R.string.need_permission, "갤러리"
                        )
                    ) {
                        needGalleryPermissionDialogState.value = false
                    }
                }
            }

            if (imageLoadErrorDialogState.value) {
                Dialog(
                    onDismissRequest = {
                        imageLoadErrorDialogState.value = false
                    },
                    properties = DialogProperties(
                        dismissOnBackPress = true,
                        dismissOnClickOutside = true
                    )
                ) {
                    NoticeDialog(
                        text = stringResource(
                            id = R.string.image_load_error
                        )
                    ) {
                        finish()
                    }
                }
            }
        }
    }
}

@Composable
private fun ReviewImagePlaceHolder(
    modifier: Modifier,
    bitmap: MutableState<ImageBitmap?>,
    onClick: () -> Unit
) {
    Box(
        modifier
            .clip(
                shape = RoundedCornerShape(12.dp)
            )
            .aspectRatio(1f)
            .background(
                color = subBackground()
            )
            .clickable {
                onClick()
            }
    ) {
        if (bitmap.value != null) {
            Image(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxSize(),
                bitmap = bitmap.value!!,
                contentDescription = "reviewImage",
                contentScale = ContentScale.Crop
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterHorizontally),
                    painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                    contentDescription = "addImageIcon",
                    tint = subTextColor()
                )

                APText(
                    modifier = Modifier
                        .padding(
                            top = 4.dp
                        )
                        .align(Alignment.CenterHorizontally),
                    text = stringResource(
                        id = R.string.add_review_image
                    ),
                    fontColor = subTextColor(),
                    fontSize = 12.sp
                )
            }
        }
    }
}