# 📚 Praktikum Room Database - Aplikasi Todo

Pada praktikum ini, kita akan belajar bagaimana mengimplementasikan **Room Database** ke dalam aplikasi Android. Kita akan membuat aplikasi **Todo List** sederhana yang menyimpan data lokal secara permanen menggunakan Room.

---

## ✅ 1. Menambahkan Dependency dan Plugin

### a. Tambahkan Plugin di `build.gradle.kts (project)`

```kotlin
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Tambahkan KSP
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false

    // Tambahkan Hilt (untuk dependency injection)
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
}
```

> ⚠️ **Catatan:**
> Pastikan versi KSP sesuai dengan versi Kotlin agar tidak terjadi error saat build.
> Lihat versi terbaru di sini: [🔗 KSP Releases](https://github.com/google/ksp/releases)

---

### b. Tambahkan Library di `build.gradle.kts (app)`

```kotlin
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

dependencies {
    // Room
    implementation("androidx.room:room-runtime:2.7.1")
    implementation("androidx.room:room-ktx:2.7.1")
    ksp("androidx.room:room-compiler:2.7.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.56.2")
    ksp("com.google.dagger:hilt-compiler:2.56.2")
}
```

---

## 🧱 2. Buat Data Entity: `TodoEntity.kt`

Buat package `data.database` dan file `TodoEntity.kt`. Tambahkan anotasi `@Entity` untuk membuat tabel.

```kotlin
package com.example.todoapp.data.database

import androidx.room.*

@Entity(tableName = "todo")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "status")
    var status: Boolean = false
)
```

---

## 📘 3. Buat DAO Interface: `TodoDao.kt`

Buat interface `TodoDao.kt` di package yang sama.

```kotlin
@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    suspend fun getAllTodos(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)
}
```

---

## 🗄️ 4. Buat Class `AppDatabase.kt`

```kotlin
@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
```

---

## 🧪 5. Setup Dependency Injection (Hilt)

Tambahkan modul untuk menyuntikkan `Room` dan `Dao` menggunakan Hilt.

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "todo_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(db: AppDatabase): TodoDao = db.todoDao()
}
```

---

## 📦 6. Buat Repository: `TodoRepositoryImpl.kt`

```kotlin
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun create(title: String, description: String?) {
        val todo = TodoEntity(title = title, description = description)
        todoDao.insertTodo(todo)
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

    override suspend fun delete(todo: Todo) {
        val entity = TodoEntity(
            id = todo.id.toInt(),
            title = todo.title,
            description = todo.description,
            status = todo.status
        )
        todoDao.deleteTodo(entity)
    }

   override suspend fun update(todo: Todo, newStatus: Boolean) {
        val entity = TodoEntity(
            id = todo.id.toInt(),
            title = todo.title,
            description = todo.description,
            status = todo.status
        )

        entity.status = newStatus
        todoDao.updateTodo(entity)
    }
}
```

---

## 🔄 Interface Repository: `TodoRepository.kt`

```kotlin
interface TodoRepository {
    suspend fun create(title: String, description: String?)
    suspend fun getAll(): List<Todo>
    suspend fun update(todo: Todo, newStatus: Boolean)
    suspend fun delete(todo: Todo)
}
```

---

## 🔧 Langkah Selanjutnya

Setelah konfigurasi Room, Repository, dan Dependency Injection selesai, **langkah selanjutnya adalah membuat Use Case, ViewModel, dan UI** untuk menghubungkan logika data dengan tampilan pengguna. Dengan pendekatan ini, kamu akan membangun arsitektur yang bersih dan scalable.

---

## 🔗 Kode Lengkap

Kamu bisa melihat implementasi lengkap dari praktikum ini di GitHub:

👉 [**PPAB-2025 - Week7A (Room Todo App)**](https://github.com/raflyamanta/PPAB-2025/tree/main/code/week-07/Week7A)

