package com.example.week7c.pages.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7c.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {
    private var _uiState = MutableStateFlow(AuthState())
    val uiState get() = _uiState.asStateFlow()

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
                authRepository.login(
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