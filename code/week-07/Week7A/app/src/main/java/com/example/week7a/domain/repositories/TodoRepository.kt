package com.example.week7a.domain.repositories

import com.example.week7a.commons.Result
import com.example.week7a.domain.models.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun create(
        title: String,
        description: String?,
    ): Flow<Result<String>>

    fun getAll(): Flow<Result<List<Todo>>>

    fun get(): Todo
    fun update(): String
    fun delete(): String
}