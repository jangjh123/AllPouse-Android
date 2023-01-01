package com.jangjh123.allpouse_android.ui.screen.main

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.model.Brand
import com.jangjh123.allpouse_android.data.model.Perfume
import com.jangjh123.allpouse_android.data.model.Post
import com.jangjh123.allpouse_android.data.remote.NoParameterRequiredData
import com.jangjh123.allpouse_android.data.repository.main.MainRepository
import com.jangjh123.allpouse_android.ui.component.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {
    private val _recommendedPerfumeListState = MutableStateFlow<UiState>(UiState.Loading)
    val recommendedPerfumeListState: StateFlow<UiState>
        get() = _recommendedPerfumeListState

    fun getNoParameterRequiredData(
        state: MutableStateFlow<UiState>,
        data: NoParameterRequiredData
    ) {
        repository.getData(
            url = when (data) {
                is NoParameterRequiredData.UserTasteKeywordList -> {
                    ""
                }
                is NoParameterRequiredData.RecommendedPerfumeList -> {
                    "api/v1/perfume"
                }
                is NoParameterRequiredData.BestPostList -> {
                    "api/v1/post/popular"
                }
                is NoParameterRequiredData.AgeGenderPopularPerfumeList -> {
                    ""
                }
                is NoParameterRequiredData.PopularBrandList -> {
                    "api/v1/brand"
                }
            },
            typeKey = when (data) {
                is NoParameterRequiredData.UserTasteKeywordList -> {
                    String::class.java
                    // todo : will be changed
                }
                is NoParameterRequiredData.RecommendedPerfumeList -> {
                    Perfume::class.java
                }
                is NoParameterRequiredData.BestPostList -> {
                    Post::class.java
                }
                is NoParameterRequiredData.AgeGenderPopularPerfumeList -> {
                    String::class.java
                    // todo : will be changed
                }
                is NoParameterRequiredData.PopularBrandList -> {
                    Brand::class.java
                }
            },
            onSuccess = { response ->
                state.value = UiState.OnSuccess(
                    data = response.data
                )
            },
            onFailure = { response ->
                state.value = UiState.OnFailure(
                    errorMessage = response.errorMessage
                )
            }
        )
    }
}