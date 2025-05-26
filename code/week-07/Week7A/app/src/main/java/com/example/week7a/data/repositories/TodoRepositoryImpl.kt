package com.example.week7a.data.repositories

import com.example.week7a.data.remote.requests.CreateTodoRequest
import com.example.week7a.data.remote.retrofit.TodoApi
import com.example.week7a.domain.models.Todo
import com.example.week7a.domain.repositories.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoApi: TodoApi
) : TodoRepository {
    companion object {
        private const val TAG = "TodoRepositoryImpl"
    }

    override suspend fun create(title: String, description: String?): String = try {
        val response = todoApi.create(CreateTodoRequest(title, description))
        val body = response.body()

        when (response.isSuccessful && body != null) {
            true -> body.id
            false -> throw Exception(response.message())
        }
    } catch (e: Exception) {
        throw e
    }

    override suspend fun getAll(): List<Todo> = try {
        val response = todoApi.getAll()
        val body = response.body()

        when (response.isSuccessful && body != null) {
            true -> body.todos.map {
                Todo(
                    id = it.id,
                    title = it.title,
                    description = it.description,
                    status = it.status
                )
            }

            false -> throw Exception(response.message())
        }
    } catch (e: Exception) {
        throw e
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