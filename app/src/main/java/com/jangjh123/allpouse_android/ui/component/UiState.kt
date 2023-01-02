package com.jangjh123.allpouse_android.ui.component

sealed class UiState {
    object Loading : UiState()
    data class OnSuccess(val data: Any? = null) : UiState()
    data class OnFailure(val errorMessage: String? = null) : UiState()
}