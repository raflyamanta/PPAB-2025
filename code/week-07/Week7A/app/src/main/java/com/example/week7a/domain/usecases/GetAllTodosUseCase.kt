package com.example.week7a.domain.usecases

import com.example.week7a.commons.Resource
import com.example.week7a.domain.models.Todo
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<Resource<List<Todo>>> = flow {
        try {
            emit(Resource.Loading)
            val result = todoRepository.getAll()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan tak terduga!"))
        }
    }
}