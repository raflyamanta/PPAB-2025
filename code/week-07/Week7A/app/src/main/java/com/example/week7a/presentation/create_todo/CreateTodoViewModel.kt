package com.example.week7a.presentation.create_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7a.commons.Resource
import com.example.week7a.domain.usecases.CreateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.week7a.presentation.create_todo.CreateTodoState as State

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    private val createTodoUseCase: CreateTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    fun create(title: String, description: String) =
        createTodoUseCase(title, description)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> _uiState.update {
                        it.copy(detail = State.Detail.Error(result.message))
                    }

                    is Resource.Loading -> _uiState.update {
                        it.copy(detail = State.Detail.Loading)
                    }

                    is Resource.Success -> _uiState.update {
                        it.copy(detail = State.Detail.Success)
                    }
                }
            }
            .launchIn(viewModelScope)
}