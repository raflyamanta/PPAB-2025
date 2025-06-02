package com.example.week7a.presentation.create_todo

data class CreateTodoState(
    val detail: Detail = Detail.Initial
) {
    interface Detail {
        data object Initial : Detail
        data object Loading : Detail
        data object Success : Detail
        data class Error(val message: String) : Detail
    }
}
