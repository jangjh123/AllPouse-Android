package com.jangjh123.allpouse_android.data.repository.detail.perfume_detail.write_review

import androidx.compose.ui.graphics.ImageBitmap
import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.model.APCallback
import com.jangjh123.allpouse_android.data.remote.model.ResponseState
import com.jangjh123.allpouse_android.util.convertBitmapToWebpFile
import com.jangjh123.allpouse_android.util.getImageBodyForMultiPart
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

class WriteReviewRepository(
    private val networkHelper: NetworkHelper
) {
    fun sendReview(
        image0: ImageBitmap?,
        image1: ImageBitmap?,
        image2: ImageBitmap?,
        title: String,
        content: String,
        perfumeId: Int,
        onSuccess: (ResponseState.OnSuccess) -> Unit,
        onFailure: (ResponseState.OnFailure) -> Unit
    ) {
        networkHelper.client()
            .postReview(
                image0 =
                if (image0 != null) getImageBodyForMultiPart(
                    "photo",
                    convertBitmapToWebpFile(image0)
                )
                else null,
                image1 =
                if (image1 != null) getImageBodyForMultiPart(
                    "photo",
                    convertBitmapToWebpFile(image1)
                )
                else null,
                image2 =
                if (image2 != null) getImageBodyForMultiPart(
                    "photo",
                    convertBitmapToWebpFile(image2)
                )
                else null,
                review = RequestBody.create(
                    "application/json".toMediaTypeOrNull(),
                    JsonObject().apply {
                        addProperty("subject", title)
                        addProperty("content", content)
                        addProperty("perfumeId", perfumeId)
                    }.toString()
                )
            ).enqueue(object : APCallback<JsonObject>() {
                override fun onSuccess(data: JsonObject) {
                    onSuccess(ResponseState.OnSuccess())
                }

                override fun onFailure(errorMessage: String) {
                    onFailure(
                        ResponseState.OnFailure(
                            errorMessage = errorMessage
                        )
                    )
                }
            })
    }
}