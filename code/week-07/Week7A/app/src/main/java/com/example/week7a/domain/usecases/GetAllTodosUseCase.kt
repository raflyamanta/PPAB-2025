package com.example.week7a.domain.usecases

import com.example.week7a.domain.models.TodoEntity
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<TodoEntity>> =
        todoRepository.getAll()
}