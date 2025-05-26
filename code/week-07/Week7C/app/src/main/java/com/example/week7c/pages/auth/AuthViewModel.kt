package com.example.week7c.pages.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7c.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private var _uiState = MutableStateFlow(AuthState())
    val uiState get() = _uiState.asStateFlow()

//    fun test() {
//        val authRepository = AuthRepository()
//        val loginUseCase = LoginUseCase(authRepository)
//        loginUseCase.call()
//    }

    fun switchMode(newValue: Boolean) = _uiState.update {
        it.copy(isLogin = newValue)
    }

    fun login(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(detail = AuthState.Detail.Loading) }
            try {
                loginUseCase.call(
                    username = username,
                    password = password
                )
                _uiState.update { it.copy(detail = AuthState.Detail.Success) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        detail = AuthState.Detail.Failure(
                            message = e.message ?: "error tidak diketahui!"
                        )
                    )
                }
            }
        }
    }

    fun register() {

    }
}