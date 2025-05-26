package com.example.week7a.pages.create_todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7a.commons.Result
import com.example.week7a.domain.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import com.example.week7a.pages.create_todo.CreateTodoState as State

@HiltViewModel
class CreateTodoViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(State())
    val uiState get() = _uiState.asStateFlow()

    fun create(title: String, description: String) =
        todoRepository
            .create(title, description)
            .onEach { result ->
                when (result) {
                    is Result.Error -> _uiState.update {
                        it.copy(detail = State.Detail.Error(result.message))
                    }

                    is Result.Loading -> _uiState.update {
                        it.copy(detail = State.Detail.Loading)
                    }

                    is Result.Success -> _uiState.update {
                        it.copy(detail = State.Detail.Success)
                    }
                }
            }
            .launchIn(viewModelScope)
}