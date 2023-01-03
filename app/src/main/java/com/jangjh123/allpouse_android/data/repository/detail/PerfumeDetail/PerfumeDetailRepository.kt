package com.jangjh123.allpouse_android.data.repository.detail.PerfumeDetail

import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.model.PerfumeDetail
import com.jangjh123.allpouse_android.data.model.PerfumeInfo
import com.jangjh123.allpouse_android.data.model.Review
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.model.APCallback
import com.jangjh123.allpouse_android.data.remote.model.ResponseState
import com.jangjh123.allpouse_android.data.remote.parseToType

class PerfumeDetailRepository(
    private val networkHelper: NetworkHelper
) {
    fun getPerfumeDetailData(
        perfumeId: Int,
        onSuccess: (ResponseState.OnSuccess) -> Unit,
        onFailure: (ResponseState.OnFailure) -> Unit
    ) {
        networkHelper.client()
            .fetchPerfumeDetail(perfumeId)
            .enqueue(object : APCallback<JsonObject>() {
                override fun onSuccess(data: JsonObject) {
                    data.get("data").asJsonObject.also { perfumeDetail ->
                        onSuccess(
                            ResponseState.OnSuccess(
                                data = PerfumeDetail(
                                    perfumeInfo = parseToType(
                                        PerfumeInfo::class.java,
                                        perfumeDetail.get("infoDto").asJsonObject
                                    ),
                                    perfumerReviews = perfumeDetail.get("perfumerReviews").asJsonArray.map { review ->
                                        parseToType(
                                            Review::class.java,
                                            review.asJsonObject
                                        )
                                    },
                                    userReviews = perfumeDetail.get("userReviews").asJsonArray.map { review ->
                                        parseToType(
                                            Review::class.java,
                                            review.asJsonObject
                                        )
                                    },
                                    highRecommendReviews = perfumeDetail.get("highRecommendReviews").asJsonArray.map { review ->
                                        parseToType(
                                            Review::class.java,
                                            review.asJsonObject
                                        )
                                    }
                                )
                            )
                        )
                    }
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