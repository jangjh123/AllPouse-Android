package com.jangjh123.allpouse_android.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.data.repository.splash.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {
    private val _signInState = MutableStateFlow<SignInState>(SignInState.Loading)
    val signInState: StateFlow<SignInState>
        get() = _signInState

    fun signIn() {
        repository.getStoredValue(
            onSuccess = {
                _signInState.value = SignInState.OnSuccess
            },
            onFailure = {
                _signInState.value = SignInState.OnFailure
            }
        )
    }
}

sealed class SignInState {
    object Loading : SignInState()
    object OnFailure : SignInState()
    object OnSuccess : SignInState()
}