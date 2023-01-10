package com.jangjh123.allpouse_android.ui.screen.detail.perfume_detail

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.repository.detail.perfume_detail.PerfumeDetailRepository
import com.jangjh123.allpouse_android.ui.component.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PerfumeDetailViewModel @Inject constructor(
    private val repository: PerfumeDetailRepository
) : ViewModel() {
    private val _perfumeDetailDataState = MutableStateFlow<UiState>(UiState.OnLoading())
    val perfumeDetailDataState: StateFlow<UiState>
        get() = _perfumeDetailDataState

    fun getPerfumeDetailScreenData(
        perfumeId: Int
    ) {
        _perfumeDetailDataState.value = UiState.OnLoading()
        repository.getPerfumeDetailData(
            perfumeId = perfumeId,
            onSuccess = { response ->
                _perfumeDetailDataState.value = UiState.OnSuccess(
                    data = response.data
                )
            },
            onFailure = { response ->
                _perfumeDetailDataState.value = UiState.OnFailure(
                    errorMessage = response.errorMessage
                )
            }
        )
    }
}