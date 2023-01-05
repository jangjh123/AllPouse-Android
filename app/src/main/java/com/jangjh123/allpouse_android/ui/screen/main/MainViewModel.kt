package com.jangjh123.allpouse_android.ui.screen.main

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.model.AdBanner
import com.jangjh123.allpouse_android.data.model.Brand
import com.jangjh123.allpouse_android.data.model.Perfume
import com.jangjh123.allpouse_android.data.model.PostWithBoardName
import com.jangjh123.allpouse_android.data.remote.NoParameterRequiredData
import com.jangjh123.allpouse_android.data.repository.main.MainRepository
import com.jangjh123.allpouse_android.ui.component.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository,
) : ViewModel() {
    private val _adBannerListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val adBannerListState: StateFlow<UiState>
        get() = _adBannerListState

    private val _userTasteKeywordListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val userTasteKeywordListState: StateFlow<UiState>
        get() = _userTasteKeywordListState

    private val _recommendedPerfumeListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val recommendedPerfumeListState: StateFlow<UiState>
        get() = _recommendedPerfumeListState

    private val _bestPostListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val bestPostListState: StateFlow<UiState>
        get() = _bestPostListState

    private val _ageGenderPopularPerfumeListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val ageGenderPopularPerfumeListState: StateFlow<UiState>
        get() = _ageGenderPopularPerfumeListState

    private val _popularBrandListState = MutableStateFlow<UiState>(UiState.OnLoading)
    val popularBrandListState: StateFlow<UiState>
        get() = _popularBrandListState

    fun getHomeScreenData(
        data: NoParameterRequiredData,
    ) {
        getNoParameterRequiredData(
            state = when (data) {
                is NoParameterRequiredData.AdBannerList -> {
                    _adBannerListState
                }
                is NoParameterRequiredData.RecommendedPerfumeList -> {
                    _recommendedPerfumeListState
                }
                is NoParameterRequiredData.BestPostList -> {
                    _bestPostListState
                }
                is NoParameterRequiredData.PopularBrandList -> {
                    _popularBrandListState
                }
                else -> {
                    _recommendedPerfumeListState
                    // not working, will be changed
                }
            },
            data = data
        )
    }

    private fun getNoParameterRequiredData(
        state: MutableStateFlow<UiState>,
        data: NoParameterRequiredData,
    ) {
        state.value = UiState.OnLoading
        repository.getData(
            url = when (data) {
                is NoParameterRequiredData.AdBannerList -> {
                    "api/v1/banner"
                }
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
                is NoParameterRequiredData.AdBannerList -> {
                    AdBanner::class.java
                }
                is NoParameterRequiredData.UserTasteKeywordList -> {
                    String::class.java
                    // todo : will be changed
                }
                is NoParameterRequiredData.RecommendedPerfumeList -> {
                    Perfume::class.java
                }
                is NoParameterRequiredData.BestPostList -> {
                    PostWithBoardName::class.java
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