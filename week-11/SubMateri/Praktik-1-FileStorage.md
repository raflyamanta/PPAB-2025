**[{{ Modul Materi (File Storage) }}](1-FileStorage.md)**

# Praktik File Storage

## Alur Praktikum

1. Buat Project Baru dengan Compose
2. Tambahkan Dependencies Dilevel Module
3. Membuat Model Data (FileModel) – Menyimpan informasi file.
4. Membuat FileHelper – Class utilitas untuk operasi baca/tulis file.
5. Menambahkan Theme sendiri
6. Menggunakan ViewModel untuk State Management – Mengelola state aplikasi.
7. Membangun UI dengan Jetpack Compose – Desain tampilan aplikasi.
8. Menjalankan aplikasi.

## 1. Buat Project Baru

| Field                        | Value                |
| ---------------------------- | -------------------- |
| Nama Project                 | MyReadWriteFile      |
| Templates                    | Phone and Tablet     |
| Tipe Activity                | Empty Views Activity (Compose Ver) |
| Language                     | Kotlin               |
| Minimum SDK                  | API level 29         |
| Build Configuration Language | Kotlin DSL           |

## 2. Tambahkan Dependencies Dilevel Module

`build.gradle.kts (Module:App)`

```
dependencies {
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation("androidx.compose.material3:material3:1.1.2")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.4")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
}
```

Berikut adalah hasil tampilannya:

![Result](Praktik-1-1Result.png)

## 3. Membuat Model Data (FileModel) – Menyimpan informasi file.
`FileModel.kt`
```kotlin
data class FileModel(
    var filename: String? = null,
    var data: String? = null
)
```

4. Membuat FileHelper – Class utilitas untuk operasi baca/tulis file.


```kotlin
internal object FileHelper {
    fun writeToFile(fileModel: FileModel, context: Context) {
        context.openFileOutput(fileModel.filename, Context.MODE_PRIVATE).use {
            it.write(fileModel.data?.toByteArray())
        }
    }

    fun readFromFile(context: Context, filename: String): FileModel {
        val fileModel = FileModel()
        fileModel.filename = filename
        fileModel.data = context.openFileInput(filename).bufferedReader().useLines { lines ->
            lines.fold("") { some, text -> "$some$text\n" }
        }
        return fileModel
    }
}
```

## 5. Menambahkan Theme sendiri



## Referensi Tambahan

[Nikoloz Akhvlediani - Scoped Storage, SAF & MediaStore](https://www.youtube.com/watch?v=8Qs8jCOgEyI)

**[{{ Modul Materi (File Storage) }}](1-FileStorage.md)**
