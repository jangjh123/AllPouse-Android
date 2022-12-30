package com.jangjh123.allpouse_android.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
const val NETWORK_ERROR_MESSAGE = "네트워크 오류입니다.\n연결 상태를 확인해 주세요."
const val UNKNOWN_ERROR_MESSAGE = "알 수 없는 오류가 발생했습니다."
class NetworkHelper {
    private val apiService = Retrofit.Builder()
        .baseUrl("http://3.37.47.117:8080/")
        .client(
            OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AllPouseApi::class.java)

    fun client(): AllPouseApi = apiService
}