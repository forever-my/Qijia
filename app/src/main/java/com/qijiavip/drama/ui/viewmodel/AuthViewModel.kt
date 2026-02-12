package com.qijiavip.drama.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qijiavip.drama.data.remote.dto.LoginResult
import com.qijiavip.drama.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    
    fun wechatLogin(code: String, nickname: String? = null, avatar: String? = null) {
        viewModelScope.launch {
            android.util.Log.d("AuthViewModel", "开始微信登录，code: $code")
            _loginState.value = LoginState.Loading
            authRepository.wechatLogin(code, nickname, avatar)
                .onSuccess { result ->
                    android.util.Log.d("AuthViewModel", "登录成功: ${result.nickname}")
                    _loginState.value = LoginState.Success(result)
                }
                .onFailure { error ->
                    android.util.Log.e("AuthViewModel", "登录失败: ${error.message}")
                    _loginState.value = LoginState.Error(error.message ?: "登录失败")
                }
        }
    }
    
    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
                .onSuccess {
                    _loginState.value = LoginState.Idle
                }
        }
    }
    
    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val result: LoginResult) : LoginState()
    data class Error(val message: String) : LoginState()
}
