package com.jangjh123.allpouse_android.data.repository.splash

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashRepository(
    private val networkHelper: NetworkHelper,
    dataStore: DataStore<Preferences>
) {

}