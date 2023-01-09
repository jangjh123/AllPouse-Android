package com.jangjh123.allpouse_android.util

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.request.ImageRequest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

fun Modifier.addFocusCleaner(
    focusManager: FocusManager,
) =
    this.pointerInput(Unit) {
        detectTapGestures {
            focusManager.clearFocus()
        }
    }

fun Modifier.clickableWithoutRipple(
    onClick: () -> Unit,
) = this.clickable(
    interactionSource = MutableInteractionSource(),
    indication = null
) {
    onClick()
}

fun convertUriToBitmap(
    contentResolver: ContentResolver,
    uri: Uri,
): Bitmap {
    return if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(
            contentResolver,
            uri
        )
    } else {
        ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, uri))
    }
}

fun startCamera(
    context: Context,
    cameraLauncher: ActivityResultLauncher<Uri>,
): Uri {
    val photoFile = File.createTempFile(
        "IMG_",
        ".webp",
        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    )

    val photoUri = FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
    cameraLauncher.launch(photoUri)
    return photoUri
}

fun startGallery(
    galleryLauncher: ActivityResultLauncher<PickVisualMediaRequest>,
) {
    galleryLauncher.launch(PickVisualMediaRequest())
}

fun getImageBitmapFromUrl(
    context: Context,
    url: String,
    onSuccess: (ImageBitmap) -> Unit,
) {
    ImageLoader(context).enqueue(
        ImageRequest.Builder(context)
            .data(url)
            .target { result ->
                onSuccess(result.toBitmap().asImageBitmap())
            }
            .build()
    )
}

fun convertBitmapToWebpFile(
    bitmap: ImageBitmap
): File {
    val webpFile = File.createTempFile(
        "IMG_",
        ".webp"
    )
    val fileOutputStream = FileOutputStream(webpFile)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.WEBP_LOSSY, 50, fileOutputStream)
        fileOutputStream.close()
    }

    return webpFile
}

fun createImageMultipartBody(key: String, file: File): MultipartBody.Part {
    return MultipartBody.Part.createFormData(
        name = key,
        filename = file.name,
        body = file.asRequestBody("image/*".toMediaType())
    )
}