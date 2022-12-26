package com.jangjh123.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "signIn")
val SOCIAL_ID = stringPreferencesKey("socialId")
val LOGIN_TYPE = stringPreferencesKey("loginType")
val ACCESS_TOKEN = stringPreferencesKey("accessToken")
val REFRESH_TOKEN = stringPreferencesKey("refreshToken")