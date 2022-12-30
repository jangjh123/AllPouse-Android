package com.jangjh123.allpouse_android.data.remote.model

import com.google.gson.Gson
import com.jangjh123.allpouse_android.data.remote.NETWORK_ERROR_MESSAGE
import com.jangjh123.allpouse_android.data.remote.UNKNOWN_ERROR_MESSAGE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class APCallback<T> : Callback<T> {
    protected abstract fun onSuccess(data: T)
    protected abstract fun onFailure(errorMessage: String)

    override fun onResponse(call: Call<T>, response: Response<T>) {
        if (response.body() != null) {
            onSuccess(response.body()!!)
        } else {
            try {
                onFailure(
                    errorMessage = Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    ).msg
                )
            } catch (e: Exception) {
                onFailure(
                    errorMessage = UNKNOWN_ERROR_MESSAGE
                )
            }
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is Exception) {
            onFailure(NETWORK_ERROR_MESSAGE)
        }
    }
}