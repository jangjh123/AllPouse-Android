package com.jangjh123.allpouse_android.data.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.Url

interface AllPouseApi {
    @POST("sign-api/sign-in?")
    fun signIn(
        @Query("socialId") socialId: String,
        @Query("loginType") loginType: String
    ): Call<JsonObject>

    @POST("sign-api/sign-up?")
    fun signUp(
        @Query("socialId") socialId: String,
        @Query("userName") userName: String,
        @Query("permission") permission: String,
        @Query("age") age: Int,
        @Query("gender") gender: String,
        @Query("loginType") loginType: String
    ): Call<JsonObject>

    @GET
    fun fetchDataByUrl(
        @Url url: String
    ): Call<JsonObject>
}