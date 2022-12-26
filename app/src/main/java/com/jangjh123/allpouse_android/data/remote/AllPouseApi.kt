package com.jangjh123.allpouse_android.data.remote

import com.google.gson.JsonObject
import retrofit2.http.POST
import retrofit2.http.Query

interface AllPouseApi {
    @POST("sign-api/sign-in?")
    suspend fun signIn(
        @Query("socialId") socialId: String,
        @Query("loginType") loginType: String
    ) : JsonObject
}