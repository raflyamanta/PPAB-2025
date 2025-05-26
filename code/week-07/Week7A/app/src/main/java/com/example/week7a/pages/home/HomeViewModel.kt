package com.example.week7a.pages.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7a.commons.Resource
import com.example.week7a.domain.usecases.GetAllTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase
) : ViewModel() {
    companion object {
        private const val TAG = "HomeViewModel"
    }

    private val _uiState = MutableStateFlow(HomeState())
    val uiState get() = _uiState.asStateFlow()

    init {
        getAllTodos()
    }

    fun getAllTodos() {
        getAllTodosUseCase().onEach { result ->
            Log.d(TAG, result.toString())
            when (result) {
                is Resource.Error -> _uiState.update {
                    it.copy(
                        detail = HomeState.Detail.Error(
                            result.message
                        )
                    )
                }

                is Resource.Loading -> _uiState.update {
                    it.copy(detail = HomeState.Detail.Loading)
                }

                is Resource.Success -> _uiState.update {
                    it.copy(
                        todos = result.data,
                        detail = HomeState.Detail.Success
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}