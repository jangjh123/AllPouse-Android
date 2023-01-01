package com.jangjh123.allpouse_android.data.remote

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.local.UserInfoObject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
                .addInterceptor(
                    AppInterceptor()
                )
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(AllPouseApi::class.java)

    fun client(): AllPouseApi = apiService
}

class AppInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("X-AUTH-TOKEN", UserInfoObject.accessToken)
            .build()
        proceed(newRequest)
    }
}

fun <T> parseToType(
    type: Class<T>,
    jsonObject: JsonObject
): T = Gson().fromJson(
    jsonObject,
    type
)
