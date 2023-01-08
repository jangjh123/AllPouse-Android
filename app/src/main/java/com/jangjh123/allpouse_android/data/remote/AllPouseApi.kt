package com.jangjh123.allpouse_android.data.remote

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface AllPouseApi {
    @POST("sign-api/sign-in?")
    fun signIn(
        @Query("socialId") socialId: String,
        @Query("loginType") loginType: String,
    ): Call<JsonObject>

    @POST("sign-api/sign-up?")
    fun signUp(
        @Query("socialId") socialId: String,
        @Query("userName") userName: String,
        @Query("permission") permission: String,
        @Query("age") age: Int,
        @Query("gender") gender: String,
        @Query("loginType") loginType: String,
    ): Call<JsonObject>

    @GET
    fun fetchDataByUrl(
        @Url url: String,
    ): Call<JsonObject>

    @GET("api/v1/rising-perfume?")
    suspend fun fetchPagedPerfumeList(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String = "ASC",
    ): JsonObject

    @GET("api/v1/perfume/{perfumeId}")
    fun fetchPerfumeDetail(
        @Path("perfumeId") perfumeId: Int,
    ): Call<JsonObject>

    @Multipart
    @POST("api/v1/review")
    fun postReview(
        @Part image0: MultipartBody.Part? = null,
        @Part image1: MultipartBody.Part? = null,
        @Part image2: MultipartBody.Part? = null,
        @Part("saveReviewDto") review: RequestBody
    ): Call<JsonObject>
}