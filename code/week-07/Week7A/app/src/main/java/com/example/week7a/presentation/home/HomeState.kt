package com.example.week7a.presentation.home

import com.example.week7a.domain.models.TodoEntity

data class HomeState(
    val todos: List<TodoEntity> = emptyList(),
    val detail: Detail = Detail.Initial,
) {
    interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Error(val message: String) : Detail
    }
}
