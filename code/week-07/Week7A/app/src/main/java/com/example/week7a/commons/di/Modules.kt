package com.example.week7a.commons.di

import com.example.week7a.data.remote.retrofit.ApiConfig
import com.example.week7a.data.remote.retrofit.TodoApi
import com.example.week7a.data.repositories.TodoRepositoryImpl
import com.example.week7a.domain.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideTodoApi(): TodoApi = ApiConfig.retrofit.create(TodoApi::class.java)

    @Provides
    fun provideTodoRepository(todoApi: TodoApi): TodoRepository = TodoRepositoryImpl(todoApi)
}