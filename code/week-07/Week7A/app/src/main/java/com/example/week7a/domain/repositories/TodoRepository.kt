package com.example.week7a.domain.repositories

import com.example.week7a.domain.models.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun create(
        title: String,
        description: String?,
    )

    fun getAll(): Flow<List<TodoEntity>>

    suspend fun updateStatus(
        todo: TodoEntity,
        status: Boolean
    )

    suspend fun delete(todo: TodoEntity)
}