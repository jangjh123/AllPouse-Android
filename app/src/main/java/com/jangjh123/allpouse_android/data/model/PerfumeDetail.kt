package com.jangjh123.allpouse_android.data.model

import com.google.gson.annotations.SerializedName

data class PerfumeDetail(
    val perfumeInfo: PerfumeInfo,
    val perfumerReviews: List<Review>,
    val userReviews: List<Review>,
    val highRecommendReviews: List<Review>
)

data class PerfumeInfo(
    val id: Int,
    @SerializedName(value = "subject")
    val perfumeName: String,
    val brandName: String,
    val price: Int,
    val content: String,
    @SerializedName(value = "hitCnt")
    val hitCount: Int,
    val path: List<String>? = null
)
