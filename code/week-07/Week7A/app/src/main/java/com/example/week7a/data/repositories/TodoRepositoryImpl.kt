package com.example.week7a.data.repositories

import com.example.week7a.commons.Result
import com.example.week7a.data.remote.requests.CreateTodoRequest
import com.example.week7a.data.remote.retrofit.TodoApi
import com.example.week7a.domain.models.Todo
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoApi: TodoApi
) : TodoRepository {
    companion object {
        private const val TAG = "TodoRepositoryImpl"
    }

    override fun create(title: String, description: String?): Flow<Result<String>> = flow {
        try {
            emit(Result.Loading)

            val response = todoApi.create(CreateTodoRequest(title, description))
            val body = response.body()

            when (response.isSuccessful && body != null) {
                true -> emit(Result.Success(body.id))
                false -> emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan tak terduga!"))
        }
    }

    override fun getAll(): Flow<Result<List<Todo>>> = flow {
        try {
            emit(Result.Loading)

            val response = todoApi.getAll()
            val body = response.body()

            when (response.isSuccessful && body != null) {
                true -> emit(
                    Result.Success(body.todos.map {
                        Todo(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            status = it.status
                        )
                    })
                )

                false -> emit(Result.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message ?: "Terjadi kesalahan tak terduga!"))
        }
    }

    override fun get(): Todo {
        TODO("Not yet implemented")
    }

    override fun update(): String {
        TODO("Not yet implemented")
    }

    override fun delete(): String {
        TODO("Not yet implemented")
    }
}