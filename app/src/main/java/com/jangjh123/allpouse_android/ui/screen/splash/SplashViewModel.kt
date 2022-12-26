package com.jangjh123.allpouse_android.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.repository.splash.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {

}