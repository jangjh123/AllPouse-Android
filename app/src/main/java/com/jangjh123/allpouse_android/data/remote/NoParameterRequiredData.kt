package com.jangjh123.allpouse_android.data.remote

sealed class NoParameterRequiredData {
    object AdBannerList : NoParameterRequiredData()
    object UserTasteKeywordList : NoParameterRequiredData()
    object RecommendedPerfumeList : NoParameterRequiredData()
    object BestPostList : NoParameterRequiredData()
    object AgeGenderPopularPerfumeList : NoParameterRequiredData()
    object PopularBrandList : NoParameterRequiredData()
}
