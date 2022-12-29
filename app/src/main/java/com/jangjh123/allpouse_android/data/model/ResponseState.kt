package com.jangjh123.allpouse_android.data.model

sealed class ResponseState {
    data class OnSuccess(val data: Any? = null) : ResponseState()
    data class OnFailure(val errorMessage: String) : ResponseState()
}