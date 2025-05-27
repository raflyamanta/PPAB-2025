## **Materi: HTTP Request & Retrofit dalam Pengembangan Aplikasi Mobile**

Berikut penjelasan yang lebih lengkap dan detail mengenai **HTTP Request dan Cara Kerjanya**:

## **1. Apa Itu HTTP Request dan Cara Kerjanya**

### **Pengertian HTTP Request**

**HTTP (Hypertext Transfer Protocol)** adalah protokol yang digunakan untuk pertukaran data di web. Dalam konteks pengembangan aplikasi mobile atau web, HTTP digunakan untuk **mengirim dan menerima data** antara client (aplikasi) dan server (backend).

**HTTP Request** adalah permintaan yang dikirim dari client ke server untuk melakukan suatu aksi tertentu, seperti mengambil data, mengirim data, memperbarui data, atau menghapus data. HTTP request dikirim ke **URL endpoint** yang merepresentasikan resource tertentu di server.

### **Tujuan HTTP Request (HTTP Methods)**

| Method   | Tujuan                    | Deskripsi                                                                                   |
| -------- | ------------------------- | ------------------------------------------------------------------------------------------- |
| `GET`    | Mengambil data            | Digunakan untuk mendapatkan resource dari server tanpa mengubah data.                       |
| `POST`   | Mengirim data baru        | Digunakan untuk mengirim data baru ke server, seperti saat login atau pendaftaran.          |
| `PUT`    | Memperbarui seluruh data  | Mengganti data yang ada dengan data baru. Biasanya digunakan untuk update seluruh resource. |
| `PATCH`  | Memperbarui sebagian data | Mengubah sebagian data saja dari resource.                                                  |
| `DELETE` | Menghapus data            | Menghapus resource dari server.                                                             |

### **Struktur Umum HTTP Request**

```plaintext
HTTP METHOD URL HTTP/VERSION
Headers
Body (opsional, tergantung method)
```

Contoh `POST` request:

```http
POST /login HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "username": "john",
  "password": "123456"
}
```

### **Cara Kerja HTTP Request (Langkah-Langkah)**

1. **Client mengirim request**

   - Misalnya, aplikasi Android mengirim `GET /users` ke `https://api.example.com`.
   - Permintaan ini dikirim ke internet melalui jaringan perangkat.

2. **Server menerima dan memproses request**

   - Server web (misalnya Express.js, Laravel, atau Spring Boot) menerima permintaan tersebut.
   - Server akan menjalankan logika tertentu: membaca database, memverifikasi input, dll.

3. **Server mengirim response**

   - Setelah memproses permintaan, server mengembalikan data ke client.
   - Data biasanya dikembalikan dalam format **JSON** atau **XML**, tergantung konfigurasi.

4. **Client memproses response**

   - Client menerima response dan menampilkannya ke user, atau menyimpannya secara lokal.

### **Contoh Response JSON**

Jika client mengirim request:

```http
GET https://api.example.com/users/1
```

Server bisa membalas dengan:

```json
{
  "id": 1,
  "name": "Jane Doe",
  "email": "jane@example.com"
}
```

### **Contoh Endpoint REST API**

```http
GET    https://api.example.com/users          // Ambil semua user
GET    https://api.example.com/users/1        // Ambil user dengan ID 1
POST   https://api.example.com/users          // Tambah user baru
PUT    https://api.example.com/users/1        // Ganti data user ID 1
PATCH  https://api.example.com/users/1        // Update sebagian data user ID 1
DELETE https://api.example.com/users/1        // Hapus user dengan ID 1
```

### **Perbedaan Client dan Server**

| Peran      | Penjelasan                                                                             |
| ---------- | -------------------------------------------------------------------------------------- |
| **Client** | Biasanya aplikasi frontend (seperti Android app, React web app) yang mengirim request. |
| **Server** | Backend yang menerima request, memprosesnya, dan mengirim response.                    |

### **Contoh Alur Nyata**

Misal kamu membuka aplikasi e-commerce di Android dan menekan tombol “Lihat Semua Produk”:

1. Aplikasi mengirim request `GET https://api.example.com/products`.
2. Server mengakses database dan mengambil data produk.
3. Server mengembalikan response:

   ```json
   [
     { "id": 1, "name": "Laptop", "price": 10000000 },
     { "id": 2, "name": "Smartphone", "price": 5000000 }
   ]
   ```

4. Aplikasi menampilkan daftar produk ke layar user.

### **Kesimpulan**

- HTTP Request adalah dasar komunikasi antara client dan server.
- Client mengirim permintaan berdasarkan HTTP method.
- Server memproses dan mengembalikan data (response).
- Aplikasi kemudian menampilkan hasil response ke user.

Berikut penjelasan yang lebih lengkap dan detail tentang **Coroutine di Kotlin**, khususnya dalam konteks pengembangan aplikasi Android, serta analoginya dengan **`async/await` di JavaScript/TypeScript**.

## **2. Coroutine di Kotlin**

### **Apa Itu Coroutine?**

**Coroutine** adalah fitur dari bahasa Kotlin untuk menangani operasi asynchronous secara efisien dan elegan. Coroutine membantu menghindari **blocking** pada thread utama (UI thread) sehingga aplikasi tetap responsif, terutama saat menjalankan operasi berat seperti:

- Memanggil API (HTTP request)
- Membaca database
- Operasi I/O (file, jaringan)
- Delay atau timer

### **Perbandingan dengan Async/Await di JS/TS**

| Konsep | JavaScript/TypeScript | Kotlin (Coroutine) |
| | -- | -- |
| Menandai fungsi async | `async function name()` | `suspend fun name()` |
| Menunggu hasil async | `await functionCall()` | `val result = functionCall()` (di scope coroutine) |
| Scope eksekusi async | Tidak eksplisit, tapi bisa dengan Promise | `CoroutineScope` seperti `lifecycleScope` |
| Concurrency model | Event loop / task queue | Structured concurrency via CoroutineScope |

### **Mengapa Perlu Coroutine di Android?**

Android hanya memiliki **1 UI Thread**. Semua interaksi UI harus terjadi di thread ini. Kalau kamu memanggil kode berat (seperti API call atau membaca file besar) di thread ini, UI akan **freeze**, dan aplikasi bisa dianggap **not responding (ANR)** oleh sistem.

Dengan coroutine, kamu bisa menjalankan tugas-tugas berat di background **tanpa mengganggu UI**.

### **Struktur Dasar Coroutine**

1. **Fungsi suspend**

   - Fungsi ini bisa “ditunda” (suspend) tanpa memblokir thread.
   - Hanya bisa dipanggil dari coroutine.

   ```kotlin
   suspend fun getUser(): User {
       return apiService.getUser()
   }
   ```

2. **Coroutine Scope**

   - Tempat coroutine dijalankan.
   - Umumnya digunakan:

     - `lifecycleScope` (Android lifecycle-aware)
     - `viewModelScope` (ViewModel)
     - `GlobalScope` (jarang direkomendasikan karena tidak lifecycle-aware)

   ```kotlin
   lifecycleScope.launch {
       val user = getUser()
       // gunakan user
   }
   ```

3. **Dispatcher**

   - Mengatur di thread mana coroutine dijalankan.

     - `Dispatchers.Main`: UI thread
     - `Dispatchers.IO`: I/O operations
     - `Dispatchers.Default`: CPU-intensive

   ```kotlin
   lifecycleScope.launch(Dispatchers.IO) {
       val user = getUser()
       withContext(Dispatchers.Main) {
           // update UI di main thread
       }
   }
   ```

### **Contoh Memanggil API**

```kotlin
// API Service
interface ApiService {
    @GET("users/1")
    suspend fun getUser(): User
}

// Di dalam Activity atau Fragment
lifecycleScope.launch {
    try {
        val user = RetrofitClient.apiService.getUser()
        // Update UI (misalnya tampilkan ke TextView)
        textViewName.text = user.name
    } catch (e: Exception) {
        // Tampilkan error
    }
}
```

> Tidak perlu pakai `Callback` manual — kode tetap bersih dan mudah dibaca.

### **Perbedaan dengan Callback Tradisional**

Tanpa coroutine (dengan callback):

```kotlin
apiService.getUser().enqueue(object : Callback<User> {
    override fun onResponse(call: Call<User>, response: Response<User>) {
        val user = response.body()
        // proses user
    }

    override fun onFailure(call: Call<User>, t: Throwable) {
        // handle error
    }
})
```

Dengan coroutine:

```kotlin
lifecycleScope.launch {
    val user = apiService.getUser()
    // proses user
}
```

> Lebih ringkas, lebih mudah di-maintain.

### **Catatan Penting**

- Fungsi `suspend` hanya bisa dipanggil dari dalam coroutine.
- Jangan jalankan coroutine di `GlobalScope` kecuali memang tidak butuh lifecycle-aware.
- Gunakan `try-catch` untuk menangani error saat memanggil API atau operasi rawan error.

### **Kesimpulan**

- Coroutine di Kotlin mempermudah pemrograman asynchronous.
- Mirip dengan `async/await` di JavaScript, tetapi dengan struktur yang lebih powerful dan lifecycle-aware.
- Menghindari UI freeze dan membuat aplikasi lebih responsif.

Berikut adalah penjelasan lebih mendalam untuk bagian **Pengenalan Retrofit**, agar mahasiswa benar-benar memahami kegunaan, arsitektur, dan cara kerjanya.

## **3. Pengenalan Retrofit **

### Apa Itu Retrofit?

**Retrofit** adalah library HTTP client untuk Android yang dikembangkan oleh Square Inc. Library ini dibuat khusus untuk mempermudah komunikasi antara aplikasi Android dan layanan berbasis **REST API**.

> Dengan Retrofit, kamu cukup mendeklarasikan endpoint API sebagai **interface**, dan Retrofit akan menangani proses request dan parsing responsenya.

### Mengapa Menggunakan Retrofit?

Sebelum Retrofit, developer Android sering menggunakan `HttpURLConnection` atau `OkHttp` secara manual, yang rawan error dan memerlukan banyak boilerplate code. Retrofit membungkus semua proses itu dalam cara yang **deklaratif**, **konsisten**, dan **terstruktur**.

### Cara Kerja Retrofit Secara Umum:

1. **Deklarasi API:** Buat interface yang mendeskripsikan endpoint REST API (misalnya: `/posts`, `/users`).
2. **Koneksi ke Server:** Retrofit menggunakan `OkHttp` sebagai HTTP client untuk mengirim request ke server.
3. **Converter:** Response dari server (biasanya dalam format JSON) akan otomatis di-convert ke objek Kotlin/Java menggunakan converter seperti **Gson** atau **Moshi**.
4. **Integrasi Coroutine:** Retrofit dapat menjalankan request secara asynchronous menggunakan `suspend` function dengan coroutine — sangat efisien dan ringkas.

### Fitur Unggulan Retrofit

| Fitur                                  | Penjelasan                                                                                                 |
| -------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| **Mudah dikonfigurasi**                | Kamu hanya perlu mendefinisikan `baseUrl` dan interface API.                                               |
| **Berbasis Annotation**                | Retrofit menggunakan anotasi seperti `@GET`, `@POST`, `@Path`, dan `@Query` untuk mendefinisikan endpoint. |
| **Support JSON Parsing**               | Bisa langsung mengubah JSON response ke data class Kotlin dengan converter (Gson, Moshi, dll).             |
| **Coroutine-friendly**                 | Bisa digunakan dengan `suspend` function untuk menulis kode asynchronous yang lebih bersih.                |
| **Support untuk berbagai HTTP Method** | Mendukung `GET`, `POST`, `PUT`, `DELETE`, `PATCH`, dll.                                                    |
| **Custom Interceptor & Logging**       | Bisa dikombinasikan dengan OkHttp interceptor untuk kebutuhan seperti logging dan autentikasi.             |

### Contoh Struktur Kode Retrofit:

```kotlin
// Interface API
interface ApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
```

```kotlin
// Konfigurasi Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://jsonplaceholder.typicode.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val api = retrofit.create(ApiService::class.java)
```

```
com.example.app
├── data
│   ├── model
│   │   └── User.kt
│   ├── network
│   │   ├── ApiService.kt
│   │   └── RetrofitClient.kt
├── ui
│   ├── UserScreen.kt
│   └── UserViewModel.kt
└── MainActivity.kt
```

Untuk lebih lengkapnya, dapat dilihat pada latihan berikut ini [Penggunaan Retrofit dan HTTP Request](./latihan.md).

# Reference

- [Official Kotlin Coroutine Guide (by JetBrains)](https://kotlinlang.org/docs/coroutines-overview.html)
- [Coroutines on Android (Android Developers)](https://developer.android.com/kotlin/coroutines)
- [Retrofit GitHub Repository (Square)](https://github.com/square/retrofit)
- [Retrofit + Kotlin Coroutine (Medium)](https://medium.com/@prakash_pun/using-retrofit-with-kotlin-coroutines-16c4bcde612b)
- [Android Developer Retrofit Guide (Android Developers Blog)](https://developer.android.com/jetpack/guide#network)
- [Martin Fowler — Data Transfer Object](https://martinfowler.com/eaaCatalog/dataTransferObject.html)
- [Kotlin Data Mapping (Medium)](https://medium.com/@tonyowen/clean-architecture-kotlin-architecture-model-mapping-68ac1e4c102d)
- [Clean Architecture — Android (Google Samples)](https://github.com/android/architecture-samples)
