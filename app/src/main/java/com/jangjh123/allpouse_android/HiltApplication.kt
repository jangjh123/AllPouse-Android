package com.jangjh123.allpouse_android

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(
            context = this,
            appKey = BuildConfig.NATIVE_APP_KEY,
        )
    }
}