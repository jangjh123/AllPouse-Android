package com.jangjh123.allpouse_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.theme.contentBackground
import com.jangjh123.allpouse_android.ui.theme.mainColor
import com.jangjh123.allpouse_android.ui.theme.subBackground
import com.jangjh123.allpouse_android.ui.theme.subTextColor

@Composable
fun APDialog(
    state: MutableState<Boolean>,
    isCancelable: Boolean? = true,
    onDismissRequest: (() -> Unit)? = null,
    block: @Composable () -> Unit,
) {
    if (state.value) {
        Dialog(
            onDismissRequest = {
                state.value = false
                onDismissRequest?.invoke()
            },
            properties = DialogProperties(
                dismissOnBackPress = isCancelable == true,
                dismissOnClickOutside = isCancelable == true
            )
        ) {
            block()
        }
    }
}

@Composable
fun SelectImageSourceDialog(
    state: MutableState<Boolean>,
    isCancelable: Boolean? = true,
    onDismissRequest: (() -> Unit)? = null,
    onClickCamera: () -> Unit,
    onClickGallery: () -> Unit,
) {
    APDialog(
        state = state,
        isCancelable = isCancelable,
        onDismissRequest = onDismissRequest
    ) {
        DefaultDialog(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier
                    .clip(
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp
                            )
                            .padding(
                                start = 12.dp,
                                end = 6.dp
                            )
                            .clip(
                                shape = RoundedCornerShape(12.dp)
                            )
                            .fillMaxHeight()
                            .weight(0.5f)
                            .background(
                                color = contentBackground()
                            )
                            .clickable {
                                onClickCamera()
                                state.value = false
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Center)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(CenterHorizontally),
                                painter = painterResource(
                                    id = R.drawable.ic_camera
                                ),
                                contentDescription = "cameraIcon",
                                tint = subTextColor()
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp
                                    )
                                    .fillMaxWidth(),
                                text = stringResource(
                                    id = R.string.take_a_picture
                                ),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .padding(
                                vertical = 12.dp
                            )
                            .padding(
                                start = 6.dp,
                                end = 12.dp
                            )
                            .clip(
                                shape = RoundedCornerShape(12.dp)
                            )
                            .fillMaxHeight()
                            .weight(0.5f)
                            .background(
                                color = contentBackground()
                            )
                            .clickable {
                                onClickGallery()
                                state.value = false
                            }
                    ) {
                        Column(
                            modifier = Modifier
                                .align(Center)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(32.dp)
                                    .align(CenterHorizontally),
                                painter = painterResource(
                                    id = R.drawable.ic_gallery
                                ),
                                contentDescription = "galleryIcon",
                                tint = subTextColor()
                            )

                            APText(
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp
                                    )
                                    .fillMaxWidth(),
                                text = stringResource(
                                    id = R.string.get_image_from_gallery
                                ),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NoticeDialog(
    state: MutableState<Boolean>,
    isCancelable: Boolean? = true,
    onDismissRequest: (() -> Unit)? = null,
    text: String,
    buttonText: String? = null,
    onClickConfirm: (() -> Unit)? = null,
) {
    APDialog(
        state = state,
        isCancelable = isCancelable,
        onDismissRequest = onDismissRequest
    ) {
        DefaultDialog(
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                ) {
                    APText(
                        modifier = Modifier
                            .align(Center),
                        text = text
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .background(
                            color = contentBackground()
                        )
                        .clickable {
                            state.value = false
                            onClickConfirm?.invoke()
                        }
                ) {
                    APText(
                        modifier = Modifier
                            .align(Center),
                        text = buttonText.let {
                            it
                        } ?: stringResource(
                            id = R.string.confirm
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingDialog() {
    Dialog(onDismissRequest = {}) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(36.dp),
            color = mainColor()
        )
    }
}

@Composable
private fun DefaultDialog(
    modifier: Modifier,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        backgroundColor = subBackground(),
        elevation = 10.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
}