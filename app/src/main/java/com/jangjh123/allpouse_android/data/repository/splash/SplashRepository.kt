package com.jangjh123.allpouse_android.data.repository.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.allpouse_android.util.ioScope
import com.jangjh123.data_store.LOGIN_TYPE
import com.jangjh123.data_store.SOCIAL_ID
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SplashRepository(
    dataStore: DataStore<Preferences>,
) {
    private val socialIdFlow = dataStore.data.map { preferences ->
        preferences[SOCIAL_ID] ?: ""
    }.flowOn(IO)

    private val loginTypeFlow = dataStore.data.map { preferences ->
        preferences[LOGIN_TYPE] ?: ""
    }.flowOn(IO)

    fun getStoredValue(
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        ioScope {
            if (socialIdFlow.first().isNotEmpty()
                && loginTypeFlow.first().isNotEmpty()
            ) {
                onSuccess()
            } else {
                onFailure()
            }
        }
    }
}