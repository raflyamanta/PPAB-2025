package com.example.week7a.data.remote.retrofit

import com.example.week7a.data.remote.requests.CreateTodoRequest
import com.example.week7a.data.remote.responses.CreateTodoResponse
import com.example.week7a.data.remote.responses.GetAllTodosResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApi {
    @POST("/api/todos")
    suspend fun create(@Body body: CreateTodoRequest): Response<CreateTodoResponse>

    @GET("/api/todos")
    suspend fun getAll(): Response<GetAllTodosResponse>

//    @GET("/api/todos/{todo_id}")
//    suspend fun get(@Path("todo_id") todoId: String): Response<Unit>
//
//    @PUT("/api/todos/{todo_id}")
//    suspend fun update(@Path("todo_id") todoId: String): Response<Unit>
//
//    @DELETE("/api/todos/{todo_id}")
//    suspend fun delete(@Path("todo_id") todoId: String): Response<Unit>
}