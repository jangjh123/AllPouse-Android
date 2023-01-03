package com.jangjh123.allpouse_android.data.model

import com.google.gson.annotations.SerializedName

data class PostWithBoardName(
    val id: Int,
    @SerializedName(value = "type")
    val board: String,
    val title: String,
    val content: String,
    val images: List<String>? = null,
    @SerializedName(value = "hitCnt")
    val hitCount: Int,
    @SerializedName(value = "recommendedCnt")
    val recommendCount: Int,
    val userId: Int,
    val userName: String,
    val createDateTime: String
)
