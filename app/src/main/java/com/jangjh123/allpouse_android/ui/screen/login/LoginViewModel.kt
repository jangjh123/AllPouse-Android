package com.jangjh123.allpouse_android.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jangjh123.allpouse_android.data.model.ResponseState
import com.jangjh123.allpouse_android.data.model.UiState
import com.jangjh123.allpouse_android.data.repository.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {
    private val _signUpState = MutableStateFlow<UiState>(UiState.Loading)
    val signUpState: StateFlow<UiState>
        get() = _signUpState

    fun signUp(
        socialId: String,
        userName: String,
        permission: String,
        age: Int,
        gender: String,
        loginType: String
    ) {
        viewModelScope.launch {
            repository.sendSignUp(
                socialId = socialId,
                userName = userName,
                permission = permission,
                age = age,
                gender = gender,
                loginType = loginType,
            ).collectLatest { response ->
                when (response) {
                    is ResponseState.OnSuccess -> {
                        _signUpState.value = UiState.OnSuccess()
                    }
                    is ResponseState.OnFailure -> {
                        _signUpState.value = UiState.OnFailure(
                            errorMessage = response.errorMessage
                        )
                    }
                }
            }
        }
    }
}