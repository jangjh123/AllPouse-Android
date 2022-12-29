package com.jangjh123.allpouse_android.data.repository.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.allpouse_android.data.local.UserInfoObject
import com.jangjh123.allpouse_android.util.Coroutine
import com.jangjh123.data_store.ACCESS_TOKEN
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

const val isTest = true

//const val isTest = false
class SplashRepository(
    dataStore: DataStore<Preferences>
) {
    private val accessTokenFlow = dataStore.data.map {
        if (isTest) {
            "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1MiIsInJvbGVzIjoiUk9MRV9VU0VSIiwiaWF0IjoxNjcyMjgwNTI4LCJleHAiOjE2NzQ4NzI1Mjh9.QaWd8UcVbeqJnVOvU5g6MIWJbrpvy0z9RwRX-g6j5qE"
        } else {
            it[ACCESS_TOKEN] ?: ""
        }
    }.flowOn(IO)

    fun getStoredValue(
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        Coroutine.io {
            accessTokenFlow.first().also { token ->
                if (token.isNotEmpty()) {
                    UserInfoObject.accessToken = token
                    onSuccess()
                } else {
                    onFailure()
                }
            }
        }
    }
}