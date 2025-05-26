package com.example.week7a.data.remote.responses

data class GetAllTodosResponse(
    val todos: List<Todo>
) {
    data class Todo(
        val id: String,
        val title: String,
        val description: String?,
        val status: Boolean
    )
}