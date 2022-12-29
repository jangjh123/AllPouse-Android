package com.jangjh123.allpouse_android.data.model

data class ErrorResponse(
    val success: Boolean,
    val error: String,
    val code: Int,
    val msg: String
)
