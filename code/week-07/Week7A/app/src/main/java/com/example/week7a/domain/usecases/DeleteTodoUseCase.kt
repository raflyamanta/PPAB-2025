package com.example.week7a.domain.usecases

import com.example.week7a.commons.Resource
import com.example.week7a.domain.models.TodoEntity
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(todo: TodoEntity): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)
            todoRepository.delete(todo)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Gagal menghapus todo"))
        }
    }
}
