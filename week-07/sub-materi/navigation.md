# Navigasi Pada Jetpack Compose

Navigasi mengacu pada interaksi yang memungkinkan pengguna menavigasi melintasi, masuk, dan keluar dari berbagai bagian konten di dalam aplikasi Anda. Komponen Navigasi menangani beragam kasus penggunaan navigasi, mulai dari klik tombol langsung hingga pola yang lebih kompleks, seperti bilah aplikasi dan laci navigasi.

## Langkah-Langkah Implementasi Navigasi

1. **Tambahkan Dependency**  
    Untuk menambahkan dukungan navigasi di projek anda, tambahkan dependency berikut di `build.gradle` aplikasimu

    ```gradle
    dependencies {
        val nav_version = "2.8.9"

        implementation("androidx.navigation:navigation-compose:$nav_version")
    }
    ```

2. **Buat NavController**  
    Navigation controller adalah salah satu konsep utama dalam navigasi. Komponen ini menyimpan graph navigasi dan mengekspos method yang memungkinkan aplikasimu berpindah antar destinasi dalam graph.
    \
    Ketika menggunakan komponen navigasi, Anda membuat navigation controller menggunakan class `NavController`. `NavController` adalah API navigasi pusat. API ini melacak destinasi mana yang telah dikunjungi user, dan memungkinkan user berpindah antar destinasi.

    ```Kotlin
    val mainNavController = rememberNavController()
    ```
    <!-- Anda harus membuat NavController di posisi yang tinggi dalam hirarki composable Anda. Ia harus cukup tinggi sehingga semua composable yang perlu mereferensikannya dapat melakukannya. Dengan demikian, Kamu dapat menggunakan NavController sebagai sumber kebenaran tunggal untuk memperbarui composable di luar layar. Hal ini mengikuti prinsip-prinsip _state hoisting_. -->

3. **Buat Destinasi Dengan Composable**  
    Destinasi disini merujuk ke layar tujuan dari navigasi, destinasi dibuat dengan composable sebagaimana kita membuat komponen dengan composable seperti biasa. Tambahkan fungsi callback sebagai parameter fungsi yang nantinya akan digunakan untuk melakukan navigasi.

    ```kotlin
    @Composable
    fun ProfileScreen(
        onNavigateToFriendsList: () -> Unit,
    ) {
        Column {
            Text("Profile for John")
            Button(onClick = { onNavigateToFriendsList() }) {
                Text("Go to Friends List")
            }
        }
    }

    @Composable
    fun FriendsListScreen(
        onNavigateToProfile: () -> Unit
    ) {
        Column{
            Text("Friends List")
            Button(onClick = { onNavigateToProfile() }) {
                Text("Go to John")
            }
        }
    }
    ```

3. **Buat NavHost**  
    Komponen navigasi menggunakan _navigation graph_ untuk mengelola navigasi aplikasi. _Navigation graph_ adalah struktur data yang mengandung setiap destinasi dari aplikasi dan koneksi antar tujuannya. 
    \
    Pemanggilan fungsi NavHost dilakukan dengan mengisi parameter navController dengan NavController yang dibuat sebelumnya dan startDestination dengan route yang akan digunakan sebagai destinasi awal. Route disini adalah identifier unik untuk setiap destinasi.
    ```kotlin
    NavHost(navController = mainNavController, startDestination = "Profile", modifier = modifier) {
        composable("Profile") { backStackEntry ->
            ProfileScreen(
                onNavigateToFriendsList = { /*...*/ },
            )
        }
        composable("FriendsList") {
            FriendsListScreen(
                onNavigateToProfile = { /*...*/ }
            )
        }
    }
    ```

4. **Navigasi Antar Layar**  
    Gunakan method `navigate` dalam `NavController` untuk berpindah antar layar.
    ```kotlin
    NavHost(navController = mainNavController, startDestination = "Profile", modifier = modifier) {
        composable("Profile") {
            ProfileScreen(
                onNavigateToFriendsList = {
                    mainNavController.navigate("FriendsList")
                }
            )
        }
        composable("FriendsList") {
            FriendsListScreen(
                onNavigateToProfile = {
                    mainNavController.navigate("Profile")
                }
            )
        }
    }
    ```

5. **Mengirim Data Antar Layar**  
    Anda dapat mengirim data antar layar menggunakan argumen. Ketika kamu membuat destinasi dengan composable, `NavBackStackEntry` tersedia sebagai parameter dan kita bisa menggunakanya untuk membaca argumen. Contoh:
    ```kotlin
    composable("Profile/{name}") { backStackEntry ->
        ProfileScreen(
            onNavigateToFriendsList = { /*...*/ },
            name = backStackEntry.arguments!!.getString("name")!!
        )
    }
    ```

    Untuk menambahkan argumen, sisipkan data kedalam string route ketika memanggil method `navigate`:
    ```kotlin
    composable("FriendsList") {
        FriendsListScreen(
            onNavigateToProfile = { name ->
                mainNavController.navigate("""Profile/${name}""")
            }
        )
    }
    ```
    Dan ini adalah screen sebelumnya yang kini menerima argumen: 
    ```kotlin
    @Composable
    fun ProfileScreen(
        name: String,
        onNavigateToFriendsList: () -> Unit,
    ) {
        Column {
            Text("Profile for ${name}")
            Button(onClick = { onNavigateToFriendsList() }) {
                Text("Go to Friends List")
            }
        }
    }

    @Composable
    fun FriendsListScreen(
        onNavigateToProfile: (name: String) -> Unit
    ) {
        Column{
            Text("Friends List")
            Button(onClick = { onNavigateToProfile("John") }) {
                Text("Go to John")
            }
            Button(onClick = { onNavigateToProfile("Doe") }) {
                Text("Go to Doe")
            }
        }
    }
    ```
---    
Pada implementasi sebelumnya, kita menggunakan string untuk mendefinisikan route, kekurangan dari metode ini adalah sulitnya mengirimkan data antar destinasi dan rentan melakukan typo ketika ingin melakukan navigasi. Untuk mengatasi ini, kita bisa menggunakan serialisasi untuk mendefinisikan route.

## Langkah-langkah memperbaiki implementasi route sebelumnya

1.  **Tambahkan dependency**  
    Tambahkan dependency berikut untuk menambahkan dukungan serialisasi dan deserialisasi untuk route yang type safe
    ```gradle
    plugins {
        // Kotlin serialization plugin for type safe routes and navigation arguments
        kotlin("plugin.serialization") version "2.0.21"
    }

    dependencies {
        // JSON serialization library, works with the Kotlin serialization plugin
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
    }
    ```
2. **Gunakan serializable object atau class untuk mendefinisikan route**  
    Route mendeskripsikan bagaimana mendapatkan destinasi dan mengandung berbagai informasi yang dibutuhkan destinasi.
    \
    Jika anda butuh mengirimkan data ke destinasi, gunakan `data class` sebagai ganti dari `object`
    ```kotlin
    @Serializable
    data class Profile(val name: String)
    @Serializable
    object FriendsList
    ```
    kemudian, gunakan route ini sebagai type dari `composable` di dalam `NavHost`
    ```kotlin
    NavHost(navController = mainNavController, startDestination = Profile(name = "John Doe"), modifier = modifier) {
        composable<Profile> {
            ProfileScreen( /*...*/ )
        }
        composable<FriendsList> {
            FriendsListScreen( /* ... */)
        }
    }
    ```
3. **Ubah proses navigasi**  
    Untuk melakukan navigasi, masukkan route tujuan sebagai argumen menggantikan string sebelumnya.
    \
    Anda bisa mendapatkan instance dari route dengan menggunakan `NavBackStackEntry.toRoute()`. Ketika anda membuat destinasi menggunakan `composable`, `NavBackStackEntry` tersedia sebagai parameter.

    ```kotlin
    composable<Profile> { backStackEntry ->
        val profile: Profile = backStackEntry.toRoute()
        ProfileScreen(
            onNavigateToFriendsList = {
                mainNavController.navigate(FriendsList)
            },
            name = profile.name
        )
    }
    composable<FriendsList> {
        FriendsListScreen(
            onNavigateToProfile = { name ->
                mainNavController.navigate(Profile(name = name))
            }
        )
    }
    ```

Materi selengkapnya dapat dikulik sendiri melalui URL berikut:  
https://developer.android.com/guide/navigation  
https://developer.android.com/develop/ui/compose/navigation  
