package com.jangjh123.allpouse_android.data.remote

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkHelper {
    companion object {
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
    }

    fun client() = apiService
}