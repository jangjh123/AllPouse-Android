package com.jangjh123.allpouse_android.data.repository.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.model.APCallback
import com.jangjh123.allpouse_android.data.remote.model.ResponseState
import com.jangjh123.allpouse_android.util.ioScope
import com.jangjh123.data_store.LOGIN_TYPE
import com.jangjh123.data_store.SOCIAL_ID

class LoginRepository(
    private val networkHelper: NetworkHelper,
    private val dataStore: DataStore<Preferences>,
) {
    fun sendSignUp(
        socialId: String,
        userName: String,
        permission: String,
        age: Int,
        gender: String,
        loginType: String,
        onSuccess: (ResponseState.OnSuccess) -> Unit,
        onFailure: (ResponseState.OnFailure) -> Unit
    ) {
        networkHelper.client()
            .signUp(
                socialId = socialId,
                userName = userName,
                permission = permission,
                age = age,
                gender = gender,
                loginType = loginType
            ).enqueue(object : APCallback<JsonObject>() {
                override fun onSuccess(data: JsonObject) {
                    onSuccess(
                        ResponseState.OnSuccess(
                            data = null
                        )
                    )
                }

                override fun onFailure(errorMessage: String) {
                    onFailure(
                        ResponseState.OnFailure(
                            errorMessage = errorMessage
                        )
                    )
                }
            })
    }

    fun storeSignedValue(
        socialId: String,
        loginType: String,
    ) {
        ioScope {
            dataStore.edit { preferences ->
                preferences[SOCIAL_ID] = socialId
                preferences[LOGIN_TYPE] = loginType
            }
        }
    }
}