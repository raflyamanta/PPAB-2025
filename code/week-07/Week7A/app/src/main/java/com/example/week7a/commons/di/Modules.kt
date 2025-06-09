package com.example.week7a.commons.di

import android.content.Context
import androidx.room.Room
import com.example.week7a.data.local.AppDatabase
import com.example.week7a.data.local.TodoDao
import com.example.week7a.data.remote.retrofit.ApiConfig
import com.example.week7a.data.remote.retrofit.TodoApi
import com.example.week7a.data.repositories.TodoRepositoryImpl
import com.example.week7a.domain.repositories.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    fun provideTodoApi(): TodoApi = ApiConfig.retrofit.create(TodoApi::class.java)

    // api example
//    @Provides
//    fun provideTodoRepository(todoApi: TodoApi): TodoRepository = TodoRepositoryImpl(todoApi)

    // room example
    @Provides
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepositoryImpl(todoDao)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTodoDao(database: AppDatabase) = database.todoDao()
}