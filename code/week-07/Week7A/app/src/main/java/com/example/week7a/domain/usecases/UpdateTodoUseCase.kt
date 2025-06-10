package com.example.week7a.domain.usecases

import com.example.week7a.commons.Resource
import com.example.week7a.domain.models.TodoEntity
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(todo: TodoEntity, status: Boolean): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            todoRepository.updateStatus(todo, status)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan saat update"))
        }
    }
}
