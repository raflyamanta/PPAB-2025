package com.example.week7a.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.week7a.domain.models.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}