# Navigasi Pada Jetpack Compose

Navigasi mengacu pada interaksi yang memungkinkan pengguna menavigasi melintasi, masuk, dan keluar dari berbagai bagian konten di dalam aplikasi Anda. Komponen Navigasi menangani beragam kasus penggunaan navigasi, mulai dari klik tombol langsung hingga pola yang lebih kompleks, seperti bilah aplikasi dan laci navigasi.

## Langkah-Langkah Implementasi Navigasi

1. **Tambahkan Dependency**  
    Untuk menambahkan dukungan navigasi di projek anda, tambahkan dependency berikut di `build.gradle` aplikasimu

    ```gradle
    dependencies {
    val nav_version = "2.8.9"

    implementation("androidx.navigation:navigation-compose:$nav_version")
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
            Text("Profile for ${name}")
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
    \
    Untuk mengirimkan informasi antar destinasi, sisipkan data kedalam string route.
    ```kotlin
    NavHost(navController = mainNavController, startDestination = "Profile/John%20Doe", modifier = modifier) {
        composable("Profile") { backStackEntry ->
            ProfileScreen(
                onNavigateToFriendsList = { },
            )
        }
        composable("FriendsList") {
            FriendsListScreen(
                onNavigateToProfile = {  }
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
    Anda dapat mengirim data antar layar menggunakan argumen. Ketika kamu membuat destinasi dengan composable, `NavBackStackEntry` tersedia sebagai parameter. Contoh:
    ```kotlin
    composable("Profile/{name}") { backStackEntry ->
        ProfileScreen(
            onNavigateToFriendsList = {
                mainNavController.navigate("FriendsList")
            },
            name = backStackEntry.arguments!!.getString("name")!!
        )
    }
    ```

    Untuk navigasi:
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