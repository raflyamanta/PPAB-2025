package com.example.week7a.domain.repositories

import com.example.week7a.domain.models.Todo

interface TodoRepository {
    suspend fun create(
        title: String,
        description: String?,
    )

    suspend fun getAll(): List<Todo>

    suspend fun update(todo: Todo, newStatus: Boolean)

    suspend fun delete(todo: Todo)
}