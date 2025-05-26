package com.example.week7a.domain.repositories

import com.example.week7a.domain.models.Todo

interface TodoRepository {
    suspend fun create(
        title: String,
        description: String?,
    ): String

    suspend fun getAll(): List<Todo>

    fun get(): Todo
    fun update(): String
    fun delete(): String
}