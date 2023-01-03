package com.jangjh123.allpouse_android.data.model

import com.google.gson.annotations.SerializedName

data class Review(
    val id: Int,
    val userName: String,
    @SerializedName(value = "subject")
    val reviewTitle: String,
    val content: String,
    val perfumeName: String,
    val brandName: String,
    @SerializedName(value = "hitCnt")
    val hitCount: Int,
    @SerializedName(value = "recommendedCnt")
    val recommendCount: Int,
    val images: List<String>?,
    val createDateTime: String
)
