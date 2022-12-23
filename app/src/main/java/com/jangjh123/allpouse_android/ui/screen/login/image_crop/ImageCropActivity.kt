package com.jangjh123.allpouse_android.ui.screen.login.image_crop

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import com.jangjh123.allpouse_android.R
import com.jangjh123.allpouse_android.ui.component.APText
import com.jangjh123.allpouse_android.ui.component.NoticeDialog
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_HEIGHT_DP
import com.jangjh123.allpouse_android.ui.screen.splash.SCREEN_WIDTH_DP
import com.jangjh123.allpouse_android.ui.theme.*
import java.io.File
import kotlin.math.roundToInt

class ImageCropActivity : ComponentActivity() {
    private lateinit var cameraPermission: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    private lateinit var galleryPermission: ActivityResultLauncher<String>
    private lateinit var galleryLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    private lateinit var imageState: MutableState<ImageBitmap>
    private var photoUri: Uri? = null
    private lateinit var croppedImageState: MutableState<ImageBitmap>

    private lateinit var needPermissionDialogState: MutableState<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRegister()

        setContent {
            AllPouseAndroidTheme {
                needPermissionDialogState = remember { mutableStateOf(false) }
                imageState = remember { mutableStateOf(ImageBitmap(1, 1)) }
                croppedImageState = remember { mutableStateOf(ImageBitmap(1, 1)) }

                val imageSource = intent.getStringExtra("imageSource")

                if (imageSource == "camera") {
                    cameraPermission.launch(Manifest.permission.CAMERA)
                } else {
                    galleryLauncher.launch(PickVisualMediaRequest())
                }

                ImageCropActivityContent(
                    imageState = imageState,
                    croppedImageState = croppedImageState
                ) {
                    setResult(
                        Activity.RESULT_OK,
                        Intent().putExtra("croppedImage", croppedImageState.value.asAndroidBitmap())
                    )
                    finish()
                }

                if (needPermissionDialogState.value) {
                    Dialog(
                        onDismissRequest = { needPermissionDialogState.value = false },
                        properties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        )
                    ) {
                        NoticeDialog(
                            text = stringResource(
                                id = R.string.need_permission,
                                if (imageSource == "camera") "카메라"
                                else "저장소"
                            )
                        ) {
                            finish()
                        }
                    }
                }
            }
        }
    }

    private fun initRegister() {
        cameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startCamera()
                } else {
                    needPermissionDialogState.value = true
                }
            }

        galleryPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startGallery()
                } else {
                    needPermissionDialogState.value = true
                }
            }

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
                if (isSuccess) {
                    if (Build.VERSION.SDK_INT < 28) {
                        val imageBitmap = MediaStore.Images.Media.getBitmap(
                            contentResolver,
                            photoUri
                        )
                        imageState.value = imageBitmap.asImageBitmap()
                    } else {
                        val source =
                            ImageDecoder.createSource(this.contentResolver, photoUri!!)
                        imageState.value = ImageDecoder.decodeBitmap(source).asImageBitmap()
                    }
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (Build.VERSION.SDK_INT < 28) {
                    val imageBitmap = MediaStore.Images.Media.getBitmap(
                        contentResolver,
                        uri
                    )
                    imageState.value = imageBitmap.asImageBitmap()
                } else {
                    val source =
                        ImageDecoder.createSource(this.contentResolver, uri!!)
                    imageState.value = ImageDecoder.decodeBitmap(source).asImageBitmap()
                }
            }
    }

    private fun startCamera() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".webp",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )
        photoUri = FileProvider.getUriForFile(this, "${packageName}.provider", photoFile)
        cameraLauncher.launch(photoUri)
    }

    private fun startGallery() {
        galleryLauncher.launch(PickVisualMediaRequest())
    }
}

@Composable
private fun ImageCropActivityContent(
    imageState: MutableState<ImageBitmap>,
    croppedImageState: MutableState<ImageBitmap>,
    onClickUseImage: () -> Unit
) {
    val imageWidthState = remember { mutableStateOf(0.dp) }
    val imageHeightState = remember { mutableStateOf(0.dp) }
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
                .background(
                    color = Color.Black
                )
        ) {
            val bitmap = imageState.value.asAndroidBitmap()
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

            val frameWidthState = remember { mutableStateOf(1) }
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

                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures { _, dragAmount ->
                                offsetX.value += dragAmount.x
                                offsetY.value += dragAmount.y
                            }
                        }
                        .transformable(
                            state = state
                        ),
                    onDraw = {
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

                        clipPath(
                            circlePath,
                            clipOp = ClipOp.Difference
                        ) {
                            drawRect(
                                color = Color(0x88000000)
                            )
                            drawOval(
                                color = Color.White,
                                topLeft = Offset(
                                    offsetX.value - sizeState.value,
                                    offsetY.value - sizeState.value
                                ),
                                size = Size(sizeState.value * 2, sizeState.value * 2),
                                style = Stroke(
                                    width = 8f,
                                    pathEffect = PathEffect.dashPathEffect(
                                        floatArrayOf(20f, 20f), 0f
                                    )
                                )
                            )
                        }
                    })
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.8f)
            ) {
                Image(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .align(Center)
                        .size(120.dp)
                        .background(
                            color = contentBackground()
                        ),
                    bitmap = croppedImageState.value,
                    contentDescription = "cropped",
                    contentScale = ContentScale.FillBounds
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f)
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(
                            color = subBackground()
                        )
                        .clickable {
                            try {
                                croppedImageState.value = Bitmap
                                    .createBitmap(
                                        imageState.value.asAndroidBitmap(),
                                        (offsetX.value - sizeState.value)
                                            .roundToInt()
                                            .apply { },
                                        (offsetY.value - sizeState.value).roundToInt(),
                                        sizeState.value.roundToInt() * 2,
                                        sizeState.value.roundToInt() * 2
                                    )
                                    .asImageBitmap()
                            } catch (e: Exception) {

                            }
                        }) {
                    APText(
                        modifier = Modifier
                            .align(Center),
                        text = stringResource(
                            id = R.string.crop_image
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxHeight()
                        .background(
                            color = mainColor()
                        )
                        .clickable {
                            onClickUseImage()
                        }
                ) {
                    APText(
                        modifier = Modifier
                            .align(Center),
                        text = stringResource(
                            id = R.string.use_cropped_image
                        ),
                        fontColor = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {

}