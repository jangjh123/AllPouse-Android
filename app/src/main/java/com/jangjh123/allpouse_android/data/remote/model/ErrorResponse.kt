package com.jangjh123.allpouse_android.data.remote.model

data class ErrorResponse(
    val success: Boolean,
    val error: String,
    val code: Int,
    val msg: String
)
