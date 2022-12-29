package com.jangjh123.allpouse_android.data.repository.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.model.ErrorResponse
import com.jangjh123.allpouse_android.data.model.ResponseState
import com.jangjh123.allpouse_android.data.remote.NETWORK_ERROR_MESSAGE
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.util.Coroutine
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback

class LoginRepository(
    private val networkHelper: NetworkHelper,
    private val dataStore: DataStore<Preferences>
) {
    fun sendSignUp(
        socialId: String,
        userName: String,
        permission: String,
        age: Int,
        gender: String,
        loginType: String
    ) = callbackFlow {
        networkHelper.client()
            .signUp(
                socialId = socialId,
                userName = userName,
                permission = permission,
                age = age,
                gender = gender,
                loginType = loginType
            )
            .enqueue(object : Callback<JsonObject> {
                override fun onResponse(
                    call: Call<JsonObject>,
                    response: retrofit2.Response<JsonObject>
                ) {
                    Coroutine.io {
                        when (response.code()) {
                            0 -> {
                                send(
                                    ResponseState.OnSuccess()
                                )
                            }
                            else -> {
                                if (response.errorBody() != null) {
                                    send(
                                        ResponseState.OnFailure(
                                            errorMessage = Gson().fromJson(
                                                response.errorBody()?.string(),
                                                ErrorResponse::class.java
                                            ).msg
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Coroutine.io {
                        send(
                            ResponseState.OnFailure(
                                errorMessage = NETWORK_ERROR_MESSAGE
                            )
                        )
                    }
                }
            })
        awaitClose()
    }
}