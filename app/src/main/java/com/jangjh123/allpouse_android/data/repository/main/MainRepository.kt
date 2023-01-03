package com.jangjh123.allpouse_android.data.repository.main

import com.google.gson.JsonObject
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.model.APCallback
import com.jangjh123.allpouse_android.data.remote.model.ResponseState
import com.jangjh123.allpouse_android.data.remote.parseToType

class MainRepository(
    private val networkHelper: NetworkHelper
) {
    fun <T> getData(
        url: String,
        typeKey: Class<T>,
        onSuccess: (ResponseState.OnSuccess) -> Unit,
        onFailure: (ResponseState.OnFailure) -> Unit
    ) {
        networkHelper.client().fetchDataByUrl(url)
            .enqueue(object : APCallback<JsonObject>() {
                override fun onSuccess(data: JsonObject) {
                    onSuccess(
                        ResponseState.OnSuccess(
                            data = data.get("dataList").asJsonArray.map { data ->
                                parseToType(
                                    typeKey,
                                    jsonObject = data as JsonObject
                                )
                            })
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
}