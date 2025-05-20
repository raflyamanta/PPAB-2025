
# Dependency Injection dengan Hilt

Hilt adalah library dependency injection (DI) untuk Android yang mengurangi boilerplate dalam melakukan DI secara manual di proyek Anda. Hilt menyediakan cara standar untuk menggunakan DI dengan menyediakan container untuk setiap kelas Android di proyek Anda dan mengelola siklus hidupnya secara otomatis. Hilt dibangun di atas library Dagger yang populer untuk mendapatkan manfaat dari pemeriksaan waktu kompilasi, kinerja waktu jalan, skalabilitas, dan dukungan Android Studio yang disediakan oleh Dagger. ([developer.android.com](https://developer.android.com/training/dependency-injection/hilt-android?utm_source=chatgpt.com))

---

## Menambahkan Dependensi

### 1. Tambahkan Plugin Hilt ke `build.gradle` Proyek

#### Groovy

```groovy
plugins {
  ...
  id 'com.google.dagger.hilt.android' version '2.56.2' apply false
}
```

#### Kotlin

```kotlin
plugins {
  ...
  id("com.google.dagger.hilt.android") version "2.56.2" apply false
}
```

### 2. Terapkan Plugin dan Tambahkan Dependensi di `app/build.gradle`

#### Groovy

```groovy
plugins {
  id 'com.google.devtools.ksp'
  id 'com.google.dagger.hilt.android'
}

android {
  ...
}

dependencies {
  implementation "com.google.dagger:hilt-android:2.56.2"
  ksp "com.google.dagger:hilt-compiler:2.56.2"
}
```

#### Kotlin

```kotlin
plugins {
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  ...
}

dependencies {
  implementation("com.google.dagger:hilt-android:2.56.2")
  ksp("com.google.dagger:hilt-android-compiler:2.56.2")
}
```

> **Catatan:** Proyek yang menggunakan Hilt dan data binding memerlukan Android Studio 4.0 atau lebih tinggi.

### 3. Aktifkan Fitur Java 8

#### Groovy

```groovy
android {
  ...
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}
```

#### Kotlin

```kotlin
android {
  ...
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
```

---

## Kelas Aplikasi Hilt

Semua aplikasi yang menggunakan Hilt harus memiliki kelas `Application` yang dianotasi dengan `@HiltAndroidApp`.

```kotlin
@HiltAndroidApp
class ExampleApplication : Application() { ... }
```

Anotasi `@HiltAndroidApp` memicu pembuatan kode Hilt, termasuk kelas dasar untuk aplikasi Anda yang berfungsi sebagai container dependency tingkat aplikasi.

---

## Menyuntikkan Dependensi ke Kelas Android

Setelah Hilt diatur di kelas `Application` dan komponen tingkat aplikasi tersedia, Hilt dapat menyediakan dependensi ke kelas Android lain yang memiliki anotasi `@AndroidEntryPoint`:

```kotlin
@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() { ... }
```

Jika Anda menganotasi kelas Android dengan `@AndroidEntryPoint`, maka Anda juga harus menganotasi kelas Android yang bergantung padanya. Misalnya, jika Anda menganotasi sebuah fragment, maka Anda juga harus menganotasi aktivitas apa pun di mana Anda menggunakan fragment tersebut.

> **Catatan:** Pengecualian berikut berlaku untuk dukungan Hilt untuk kelas Android:
>
> - Hilt hanya mendukung aktivitas yang memperluas `ComponentActivity`, seperti `AppCompatActivity`.
> - Hilt hanya mendukung fragment yang memperluas `androidx.Fragment`.
> - Hilt tidak mendukung fragment yang dipertahankan.

Anotasi `@AndroidEntryPoint` menghasilkan komponen Hilt individual untuk setiap kelas Android di proyek Anda. Komponen ini dapat menerima dependensi dari kelas induk masing-masing seperti yang dijelaskan dalam hierarki komponen.

---

## Referensi Tambahan

- [Dependency injection with Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- [Hilt and Dagger annotations cheat sheet](https://developer.android.com/training/dependency-injection/hilt-cheatsheet)
- [Hilt testing guide](https://developer.android.com/training/dependency-injection/hilt-testing)
