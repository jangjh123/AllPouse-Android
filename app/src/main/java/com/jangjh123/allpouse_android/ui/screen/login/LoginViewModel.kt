package com.jangjh123.allpouse_android.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jangjh123.allpouse_android.data.repository.login.LoginRepository
import com.jangjh123.allpouse_android.ui.component.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {
    private val _signUpState = MutableStateFlow<UiState>(UiState.OnLoading)
    val signUpState: StateFlow<UiState>
        get() = _signUpState

    fun signUp(
        socialId: String,
        userName: String,
        permission: String,
        age: String,
        gender: String,
        loginType: String,
    ) {
        viewModelScope.launch {
            repository.sendSignUp(
                socialId = socialId,
                userName = userName,
                permission = permission,
                age = age,
                gender = gender,
                loginType = loginType,
                onSuccess = {
                    _signUpState.value = UiState.OnSuccess()
                    repository.storeSignedValue(
                        socialId = socialId,
                        loginType = loginType
                    )
                },
                onFailure = { response ->
                    _signUpState.value = UiState.OnFailure(
                        errorMessage = response.errorMessage
                    )
                }
            )
        }
    }
}