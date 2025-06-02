## Praktik SharedPreferences dengan Jetpack Compose

Pada praktik ini, kita akan membangun aplikasi penyimpanan data sederhana menggunakan SharedPreferences dengan Jetpack Compose. Berikut adalah alur dan contoh kode Compose yang dapat digunakan.

### 1. Buat Project Baru

| Field                        | Value                |
| ---------------------------- | -------------------- |
| Nama Project                 | MySharedPreferences  |
| Templates                    | Empty Activity       |
| Language                     | Kotlin               |
| Minimum SDK                  | API level 20         |
| Build Configuration Language | Kotlin DSL           |

### 2. Tambahkan Dependensi

Pastikan `build.gradle.kts (app)` sudah mengaktifkan Compose dan menambahkan dependensi berikut:

```kotlin
android {
    ...
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
}
dependencies {
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.compose.material:material:1.5.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.0")
}
```

### 3. Data Class User

```kotlin
data class UserModel(
    var name: String = "",
    var email: String = "",
    var age: Int = 0,
    var phoneNumber: String = "",
    var isLove: Boolean = false
)
```

### 4. UserPreference Class

```kotlin
class UserPreference(context: Context) {
    private val prefs = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    fun setUser(user: UserModel) {
        prefs.edit().apply {
            putString("name", user.name)
            putString("email", user.email)
            putInt("age", user.age)
            putString("phone", user.phoneNumber)
            putBoolean("islove", user.isLove)
            apply()
        }
    }

    fun getUser(): UserModel {
        return UserModel(
            name = prefs.getString("name", "") ?: "",
            email = prefs.getString("email", "") ?: "",
            age = prefs.getInt("age", 0),
            phoneNumber = prefs.getString("phone", "") ?: "",
            isLove = prefs.getBoolean("islove", false)
        )
    }
}
```

### 5. MainActivity dengan Jetpack Compose

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userPreference = UserPreference(this)
        setContent {
            var user by remember { mutableStateOf(userPreference.getUser()) }
            var isEditing by remember { mutableStateOf(false) }

            Surface(modifier = Modifier.fillMaxSize()) {
                if (isEditing) {
                    UserForm(
                        user = user,
                        onSave = {
                            userPreference.setUser(it)
                            user = it
                            isEditing = false
                        },
                        onCancel = { isEditing = false }
                    )
                } else {
                    UserProfile(
                        user = user,
                        onEdit = { isEditing = true }
                    )
                }
            }
        }
    }
}
```

### 6. Composable UserProfile

```kotlin
@Composable
fun UserProfile(user: UserModel, onEdit: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${user.name.ifEmpty { "Tidak Ada" }}")
        Text("Email: ${user.email.ifEmpty { "Tidak Ada" }}")
        Text("Phone: ${user.phoneNumber.ifEmpty { "Tidak Ada" }}")
        Text("Age: ${if (user.age == 0) "Tidak Ada" else user.age}")
        Text("Suka Manchester United? ${if (user.isLove) "Ya" else "Tidak"}")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onEdit) {
            Text(if (user.name.isEmpty()) "Simpan" else "Ubah")
        }
    }
}
```

### 7. Composable UserForm

```kotlin
@Composable
fun UserForm(
    user: UserModel,
    onSave: (UserModel) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(user.name) }
    var email by remember { mutableStateOf(user.email) }
    var phone by remember { mutableStateOf(user.phoneNumber) }
    var age by remember { mutableStateOf(if (user.age == 0) "" else user.age.toString()) }
    var isLove by remember { mutableStateOf(user.isLove) }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("No Handphone") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = age,
            onValueChange = { if (it.all { c -> c.isDigit() }) age = it },
            label = { Text("Age") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Suka Manchester United?")
            Checkbox(
                checked = isLove,
                onCheckedChange = { isLove = it }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Button(onClick = {
                if (name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && age.isNotBlank()) {
                    onSave(
                        UserModel(
                            name = name,
                            email = email,
                            phoneNumber = phone,
                            age = age.toIntOrNull() ?: 0,
                            isLove = isLove
                        )
                    )
                }
            }) {
                Text("Simpan")
            }
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedButton(onClick = onCancel) {
                Text("Batal")
            }
        }
    }
}
```

---

Referensi tambahan:  
- [Philipp Lackner - Sharing Data with SharedPreferences](https://www.youtube.com/watch?v=wtpRp2IpCSo)