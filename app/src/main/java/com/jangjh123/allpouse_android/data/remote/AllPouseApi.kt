package com.jangjh123.allpouse_android.data.remote

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

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

    @GET("api/v1/perfume/{perfumeId}")
    fun fetchPerfumeDetail(
        @Path("perfumeId") perfumeId: Int,
    ): Call<JsonObject>
}