package com.jangjh123.allpouse_android.ui.screen.splash

import androidx.lifecycle.ViewModel
import com.jangjh123.allpouse_android.ui.component.UiState
import com.jangjh123.allpouse_android.data.repository.splash.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: SplashRepository
) : ViewModel() {
    private val _signInState = MutableStateFlow<UiState>(UiState.OnLoading())
    val signInState: StateFlow<UiState>
        get() = _signInState

    fun signIn() {
        repository.getStoredValue(
            onSuccess = {
                _signInState.value = UiState.OnSuccess(
                    data = null
                )
            },
            onFailure = {
                _signInState.value = UiState.OnFailure(
                    errorMessage = null
                )
            }
        )
    }
}