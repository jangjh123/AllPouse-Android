package com.jangjh123.allpouse_android.ui.screen.base

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.jangjh123.allpouse_android.util.convertUriToBitmap
import com.jangjh123.allpouse_android.util.startCamera
import com.jangjh123.allpouse_android.util.startGallery
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class GetImageBaseActivity : ComponentActivity() {
    lateinit var cameraPermission: ActivityResultLauncher<String>
    private lateinit var cameraLauncher: ActivityResultLauncher<Uri>

    private lateinit var galleryPermission: ActivityResultLauncher<String>
    lateinit var galleryLauncher: ActivityResultLauncher<PickVisualMediaRequest>

    lateinit var imageState: MutableState<ImageBitmap?>
    private var photoUri: Uri? = null

    val needCameraPermissionDialogState = mutableStateOf(false)
    val needGalleryPermissionDialogState = mutableStateOf(false)
    val imageLoadErrorDialogState = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRegister()
    }

    private fun initRegister() {
        cameraPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    photoUri = startCamera(
                        context = this@GetImageBaseActivity,
                        cameraLauncher = cameraLauncher,
                    )
                } else {
                    needCameraPermissionDialogState.value = true
                }
            }

        galleryPermission =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startGallery(
                        galleryLauncher = galleryLauncher
                    )
                } else {
                    needGalleryPermissionDialogState.value = true
                }
            }

        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
                if (isSuccess) {
                    photoUri?.let {
                        imageState.value =
                            convertUriToBitmap(contentResolver, it).asImageBitmap()
                    } ?: run {
                        imageLoadErrorDialogState.value = true
                    }
                }
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                uri?.let {
                    imageState.value = convertUriToBitmap(contentResolver, it).asImageBitmap()
                } ?: run {
                    imageLoadErrorDialogState.value = true
                }
            }
    }
}