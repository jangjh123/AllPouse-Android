package com.jangjh123.allpouse_android.ui.screen.image_crop

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.RoundedCornerIconButton
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_HEIGHT_DP
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_WIDTH_DP
import com.jangjh123.allpouse_android.ui.theme.AllPouseAndroidTheme
import com.jangjh123.allpouse_android.ui.theme.background
import com.jangjh123.allpouse_android.ui.theme.contentBackground
import kotlin.math.roundToInt

class ImageCropActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AllPouseAndroidTheme {
                ImageCropActivityContent(this@ImageCropActivity)
            }
        }
    }
}

@Composable
private fun ImageCropActivityContent(context: Context) {
// todo : 고정되는 값에 대해서는 파라미터를 통해 넘겨주도록 수정
    val imageState = remember { mutableStateOf(ImageBitmap(1, 1)) }
    val imageWidthState = remember { mutableStateOf(0.dp) } // dp
    val imageHeightState = remember { mutableStateOf(0.dp) }
    val croppedImageState = remember { mutableStateOf(ImageBitmap(1, 1)) }
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    val movedState = remember { mutableStateOf(false) }
    val sizeState = remember { mutableStateOf(160f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.6f)
                .background(color = Color.Black)
        ) {

            val bitmap = ContextCompat.getDrawable(context, R.drawable.ad_banner_0)!!.toBitmap()
            val bitmapRatio = (bitmap.width).toFloat() / (bitmap.height).toFloat()

            if (bitmapRatio > 1.0) {
                imageWidthState.value = SCREEN_WIDTH_DP
                imageHeightState.value = imageWidthState.value / bitmapRatio
            } else if (bitmapRatio < 1.0) {
                imageHeightState.value = SCREEN_HEIGHT_DP * 0.6f
                imageWidthState.value = imageHeightState.value * bitmapRatio
            } else {
                imageWidthState.value = SCREEN_HEIGHT_DP * 0.5f
                imageHeightState.value = SCREEN_HEIGHT_DP * 0.5f
            }

            val frameWidthState = remember { mutableStateOf(1) } // px
            val frameHeightState = remember { mutableStateOf(1) }

            with(LocalDensity.current) {
                frameWidthState.value = imageWidthState.value.toPx().toInt()
                frameHeightState.value = imageHeightState.value.toPx().toInt()
            }

            Box(
                modifier = Modifier
                    .width(imageWidthState.value)
                    .height(imageHeightState.value)
                    .align(Center)
            ) {
                imageState.value = BitmapDrawable(
                    LocalContext.current.resources,
                    Bitmap.createScaledBitmap(
                        bitmap,
                        frameWidthState.value,
                        frameHeightState.value,
                        true
                    )
                ).bitmap.asImageBitmap()

                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = imageState.value,
                    contentDescription = "displayImage",
                    contentScale = ContentScale.FillBounds
                )

                if (offsetX.value - sizeState.value < 0) {
                    offsetX.value = sizeState.value
                }
                if (offsetY.value - sizeState.value < 0) {
                    offsetY.value = sizeState.value
                }
                if (offsetX.value + sizeState.value > frameWidthState.value) {
                    offsetX.value = frameWidthState.value - sizeState.value
                }
                if (offsetY.value + sizeState.value > frameHeightState.value) {
                    offsetY.value = frameHeightState.value - sizeState.value
                }
                if (sizeState.value * 2 > frameWidthState.value) {
                    sizeState.value = (frameWidthState.value / 2).toFloat()
                }
                if (sizeState.value * 2 > frameHeightState.value) {
                    sizeState.value = (frameHeightState.value / 2).toFloat()
                }

                val state = rememberTransformableState { zoomChange, _, _ ->
                    sizeState.value *= zoomChange
                }

                Canvas(modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectDragGestures { _, dragAmount ->
                            offsetX.value += dragAmount.x
                            offsetY.value += dragAmount.y
                        }
                    }
                    .transformable(state = state), onDraw = {
                    val circlePath = Path().apply {
                        addOval(
                            Rect(
                                if (!movedState.value) {
                                    offsetX.value = center.x
                                    offsetY.value = center.y
                                    movedState.value = true
                                    center
                                } else {
                                    Offset(offsetX.value, offsetY.value)
                                }, sizeState.value
                            )
                        )
                    }

                    clipPath(circlePath, clipOp = ClipOp.Difference) {
                        drawRect(color = Color(0x88000000))
                        drawOval(
                            color = Color.White,
                            topLeft = Offset(
                                offsetX.value - sizeState.value,
                                offsetY.value - sizeState.value
                            ),
                            size = Size(sizeState.value * 2, sizeState.value * 2),
                            style = Stroke(
                                width = 8f, pathEffect = PathEffect.dashPathEffect(
                                    floatArrayOf(20f, 20f), 0f
                                )
                            )
                        )
                    }
                })
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        ) {
            RoundedCornerIconButton(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth()
                    .height(50.dp),
                text = stringResource(
                    id = R.string.cut_image
                ),
                icon = painterResource(
                    id = R.drawable.ic_cut
                )
            ) {
                croppedImageState.value = Bitmap
                    .createBitmap(
                        imageState.value.asAndroidBitmap(),
                        (offsetX.value - sizeState.value).roundToInt(),
                        (offsetY.value - sizeState.value).roundToInt(),
                        sizeState.value.roundToInt() * 2,
                        sizeState.value.roundToInt() * 2
                    )
                    .asImageBitmap()
            }

            Image(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clip(CircleShape)
                    .align(Center)
                    .size(120.dp)
                    .background(color = contentBackground()),
                bitmap = croppedImageState.value, contentDescription = "cropped",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {

}