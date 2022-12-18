package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.write_review

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.APTextField
import com.jangjh123.allpouse_android.ui.component.FontType
import com.jangjh123.allpouse_android.ui.component.GradientButton
import com.jangjh123.allpouse_android.ui.theme.background
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor

@Composable
fun WriteReviewScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = background()
            )
    ) {
        val scrollState = rememberScrollState()
        val focusManager = LocalFocusManager.current
        val titleTextFieldState = remember { mutableStateOf("") }
        val image0State = remember { mutableStateOf<ImageBitmap?>(null) }
        val image1State = remember { mutableStateOf<ImageBitmap?>(null) }
        val image2State = remember { mutableStateOf<ImageBitmap?>(null) }
        val bodyTextFieldState = remember { mutableStateOf("") }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(
                    state = scrollState
                )
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
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            APTextField(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                textFieldState = titleTextFieldState,
                onValueChanged = { titleTextFieldState.value = it },
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
                fontSize = 20.sp,
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

                }

                ReviewImagePlaceHolder(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    bitmap = image1State,
                ) {

                }

                ReviewImagePlaceHolder(
                    modifier = Modifier
                        .padding(12.dp)
                        .weight(1f),
                    bitmap = image2State,
                ) {

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
                fontSize = 20.sp,
                fontType = FontType.Bold
            )

            APTextField(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .heightIn(
                        min = 300.dp
                    ),
                textFieldState = bodyTextFieldState,
                onValueChanged = { bodyTextFieldState.value = it },
                focusManager = focusManager,
                singleLine = false
            )

            // 내용 입력에 대한 자동 스크롤 처리
            LaunchedEffect(scrollState.maxValue) {
                if (bodyTextFieldState.value.isNotEmpty()) {
                    scrollState.scrollTo(scrollState.maxValue)
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
                    text = "Chanel No.5",
                    fontType = FontType.Bold,
                    fontSize = 20.sp
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
                    fontSize = 20.sp,
                    fontColor = subTextColor()
                )
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
                modifier = modifier
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
                    .fillMaxSize(),
                bitmap = bitmap.value!!,
                contentDescription = "reviewImage",
                contentScale = ContentScale.Inside
            )
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
            ) {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .align(CenterHorizontally),
                    painter = painterResource(
                        id = R.drawable.ic_add
                    ),
                    contentDescription = "addImageIcon",
                    tint = subTextColor()
                )

                APText(
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(CenterHorizontally),
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

@Preview(showBackground = true)
@Composable
private fun Preview() {
    WriteReviewScreen()
}