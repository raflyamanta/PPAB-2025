package com.example.week7a.pages.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7a.data.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    private var _uiState = MutableStateFlow(AuthState())
    val uiState get() = _uiState.asStateFlow()

    fun login(
        username: String,
        password: String,
    ) {
        viewModelScope.launch {
            _uiState.update { it.copy(detail = AuthState.Detail.Loading) }
            try {
                repository.login(username, password)
                _uiState.update { it.copy(detail = AuthState.Detail.Success) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        detail = AuthState.Detail.Failure(
                            message = e.message ?: "terjadi kesalahan tak terduga!"
                        )
                    )
                }
            }
        }
    }

    fun register(
        name: String,
        username: String,
        password: String,
    ) {

    }
}