package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail.write_review

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.repository.detail.perfume_detail.write_review.WriteReviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WriteReviewViewModel @Inject constructor(
    private val repository: WriteReviewRepository
) : ViewModel() {

}