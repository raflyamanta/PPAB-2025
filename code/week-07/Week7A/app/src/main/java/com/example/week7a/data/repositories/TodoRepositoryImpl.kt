package com.example.week7a.data.repositories

import android.R.attr.description
import android.util.Log.d
import android.util.Log.e
import com.example.week7a.data.local.TodoDao
import com.example.week7a.data.local.TodoEntity
import com.example.week7a.data.remote.requests.CreateTodoRequest
import com.example.week7a.data.remote.retrofit.TodoApi
import com.example.week7a.domain.models.Todo
import com.example.week7a.domain.repositories.TodoRepository
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
//    private val todoApi: TodoApi
    private val todoDao: TodoDao
) : TodoRepository {
    companion object {
        private const val TAG = "TodoRepositoryImpl"
    }

    override suspend fun create(title: String, description: String?): String = try {
        val todo = TodoEntity(title = title, description = description, status = false)
        todoDao.insertTodo(todo)
        "Berhasil menambahkan todo!"
    } catch (e: Exception) {
        throw e
    }

    override suspend fun getAll(): List<Todo> {
        return todoDao.getAllTodos().map {
            Todo(
                id = it.id.toString(),
                title = it.title,
                description = it.description,
                status = it.status
            )
        }
    }

    override suspend fun delete(todo: Todo): String = try {
        val entity = TodoEntity(
            id = todo.id.toInt(),
            title = todo.title,
            description = todo.description,
            status = todo.status
        )
        todoDao.deleteTodo(entity)
        "Berhasil menghapus todo"
    } catch (e: Exception) {
        throw e
    }


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

    override fun get(): Todo {
        TODO("Not yet implemented")
    }

    override fun update(): String {
        TODO("Not yet implemented")
    }
}