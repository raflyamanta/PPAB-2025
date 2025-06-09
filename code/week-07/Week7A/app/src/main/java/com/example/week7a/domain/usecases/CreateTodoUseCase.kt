package com.example.week7a.domain.usecases

import com.example.week7a.commons.Resource
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(
        title: String,
        description: String?,
    ): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading)

            if (title.isBlank()) {
                emit(Resource.Error("Judul todo tidak boleh kosong!"))
                return@flow
            }

            todoRepository.create(title, description)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan tak terduga!"))
        }
    }
}