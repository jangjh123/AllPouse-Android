package com.jangjh123.allpouse_android.data.model

import com.google.gson.annotations.SerializedName

data class Perfume(
    val brandId: Int,
    val brandName: String,
    val id: Int,
    val imagePath: List<String>?,
    @SerializedName(value = "subject")
    val perfumeName: String
)
