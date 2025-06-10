package com.example.week7a.data.repositories

import com.example.week7a.data.local.TodoDao
import com.example.week7a.domain.models.TodoEntity
import com.example.week7a.domain.repositories.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
//    private val todoApi: TodoApi
    private val todoDao: TodoDao
) : TodoRepository {
    companion object {
        private const val TAG = "TodoRepositoryImpl"
    }

    override suspend fun create(title: String, description: String?) {
        val todo = TodoEntity(
            title = title,
            description = description,
            status = false
        )
        todoDao.create(todo)
    }

    override fun getAll(): Flow<List<TodoEntity>> =
        todoDao.getAll()

    override suspend fun updateStatus(todo: TodoEntity, status: Boolean) {
        todo.status = status
        todoDao.update(todo)
    }

    override suspend fun delete(todo: TodoEntity) =
        todoDao.delete(todo)

//    override suspend fun create(title: String, description: String?): String = try {
//        val response = todoApi.create(CreateTodoRequest(title, description))
//        val body = response.body()
//
//        when (response.isSuccessful && body != null) {
//            true -> body.id
//            false -> throw Exception(response.message())
//        }
//    } catch (e: Exception) {
//        throw e
//    }

//    override suspend fun getAll(): List<Todo> = try {
//        val response = todoApi.getAll()
//        val body = response.body()
//
//        when (response.isSuccessful && body != null) {
//            true -> body.todos.map {
//                Todo(
//                    id = it.id,
//                    title = it.title,
//                    description = it.description,
//                    status = it.status
//                )
//            }
//
//            false -> throw Exception(response.message())
//        }
//    } catch (e: Exception) {
//        throw e
//    }

}