package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.write_review

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.repository.detail.perfume_detail.write_review.WriteReviewRepository
import com.jangjh123.allpouse_android.ui.component.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val repository: WriteReviewRepository
) : ViewModel() {

    private val _sendReviewState = MutableStateFlow<UiState>(UiState.OnLoading())
    val sendReviewState: StateFlow<UiState>
        get() = _sendReviewState

    fun setReviewToPost(
        image0: ImageBitmap?,
        image1: ImageBitmap?,
        image2: ImageBitmap?,
        title: String,
        content: String,
        perfumeId: Int
    ) {
        repository.sendReview(
            image0 = image0,
            image1 = image1,
            image2 = image2,
            title = title,
            content = content,
            perfumeId = perfumeId,
            onSuccess = {
                _sendReviewState.value = UiState.OnSuccess()
            },
            onFailure = { response ->
                _sendReviewState.value = UiState.OnFailure(
                    errorMessage = response.errorMessage
                )
            }
        )
    }
}