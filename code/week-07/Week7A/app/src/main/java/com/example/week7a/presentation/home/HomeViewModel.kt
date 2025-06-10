package com.example.week7a.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.week7a.commons.Resource
import com.example.week7a.domain.models.TodoEntity
import com.example.week7a.domain.usecases.DeleteTodoUseCase
import com.example.week7a.domain.usecases.GetAllTodosUseCase
import com.example.week7a.domain.usecases.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllTodosUseCase: GetAllTodosUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
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
//            _uiState.update { it.copy(detail = HomeState.Detail.Loading) }
//            Log.d(TAG, "getAllTodos: $result")
            _uiState.update {
                it.copy(
                    detail = HomeState.Detail.Success,
                    todos = result
                )
            }
        }.launchIn(viewModelScope)
    }

    fun deleteTodo(todo: TodoEntity) {
        deleteTodoUseCase(todo).onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.update {
                    it.copy(detail = HomeState.Detail.Loading)
                }

                is Resource.Success -> Unit

                is Resource.Error -> _uiState.update {
                    it.copy(detail = HomeState.Detail.Error(result.message))
                }
            }
        }.launchIn(viewModelScope)
    }

    fun updateTodo(todo: TodoEntity, status: Boolean) {
        updateTodoUseCase(todo, status).onEach { result ->
            when (result) {
                is Resource.Loading -> _uiState.update {
                    it.copy(detail = HomeState.Detail.Loading)
                }

                is Resource.Success -> Unit

                is Resource.Error -> _uiState.update {
                    it.copy(detail = HomeState.Detail.Error(result.message))
                }
            }
        }.launchIn(viewModelScope)
    }
}

