**[{{ Modul Materi (DataStore) }}](3-DataStore.md)**

# Praktik DataStore dengan Jetpack Compose

## Alur Praktikum

1. Membuat project baru.
2. Mengatur tampilan menggunakan Jetpack Compose.
3. Menambahkan kode untuk mengatur tema aplikasi.
4. Menambahkan library DataStore ke dalam project.
5. Membuat class baru untuk mengatur DataStore.
6. Membuat class ViewModel untuk fitur dan menghubungkan antara UI dengan DataStore.
7. Mengimplementasikan ViewModel ke dalam Activity/Composable.
8. Menjalankan aplikasi.

## 1. Membuat Project Baru

| Field                        | Value                |
| ---------------------------- | -------------------- |
| Nama Project                 | MyDataStore          |
| Templates                    | Phone and Tablet     |
| Tipe Activity                | Empty Activity       |
| Language                     | Kotlin               |
| Minimum SDK                  | API level 30         |
| Build Configuration Language | Kotlin DSL           |

## 2. Mengatur Tampilan dengan Jetpack Compose

`MainActivity.kt`

```kotlin
@Composable
fun MainScreen(
    isDarkMode: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Dark Mode",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(16.dp))
            Switch(
                checked = isDarkMode,
                onCheckedChange = onThemeChange
            )
        }
    }
}
```

---

## 3. Menambahkan Kode untuk Mengatur Tema Aplikasi

`MainActivity.kt`

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val viewModel: MainViewModel = viewModel(
            factory = ViewModelFactory(pref)
        )

        setContent {
            val isDarkMode by viewModel.getThemeSettings().observeAsState(initial = false)
            MyDataStoreTheme(darkTheme = isDarkMode) {
                MainScreen(
                    isDarkMode = isDarkMode,
                    onThemeChange = { viewModel.saveThemeSetting(it) }
                )
            }
        }
    }
}
```

Pastikan Anda sudah membuat `MyDataStoreTheme` sesuai template Compose.
### Cara Implementasi Tema dengan Jetpack Compose

Buat file `Theme.kt` di package `ui.theme`:

```kotlin
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    // Sesuaikan warna sesuai kebutuhan
)

private val LightColorScheme = lightColorScheme(
    // Sesuaikan warna sesuai kebutuhan
)

@Composable
fun MyDataStoreTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}
```

Pastikan untuk menyesuaikan warna pada `DarkColorScheme` dan `LightColorScheme` sesuai kebutuhan aplikasi Anda.
---

## 4. Menambahkan Library DataStore ke dalam Project

`build.gradle.kts (module:app)`

```gradle
dependencies {
    ...
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
}
```

---

## 5. Membuat Class Baru untuk Mengatur DataStore

`SettingsPreferences.kt`

```kotlin
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
```

## 6. Membuat Class ViewModel

`MainViewModel.kt`

```kotlin
class MainViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}
```

`ViewModelFactory.kt`

```kotlin
class ViewModelFactory(private val pref: SettingPreferences) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
```

## 7. Mengimplementasikan ViewModel ke dalam Activity/Composable

Sudah dicontohkan pada langkah 3, ViewModel di-inject ke dalam `setContent` dan state di-observe menggunakan `observeAsState`.

---


![Tampilan Akhir](Praktik-3-Result.gif)

---

**[{{ Modul Materi (DataStore) }}](3-DataStore.md)**

