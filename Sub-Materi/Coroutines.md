# Coroutines

Concurrency dalam Kotlin mengacu pada kemampuan untuk menjalankan beberapa tugas secara bersamaan atau berselingan, terutama dalam lingkungan komputasi yang dapat dijalankan secara parallel. Ini memungkinkan program untuk melakukan beberapa operasi secara efisien tanpa harus menunggu operasi sebelumnya selesai.

## Concurency Vs Parallelism

### Concurency 
Concurrency dalam Kotlin sering diimplementasikan menggunakan coroutine, yang memungkinkan penulisan kode yang terlihat bersamaan tanpa perlu membuat banyak thread fisik tambahan. Dengan coroutine, kita dapat menjalankan tugas-tugas secara konkuren tanpa overhead yang terkait dengan pembuatan dan pengelolaan thread.

### Parallelism 
Paralelisme dalam Kotlin dapat dicapai dengan menggunakan konsep konkurensi dan alat yang dirancang khusus untuk menjalankan tugas-tugas secara paralel di beberapa core CPU. Pustaka standar Kotlin, seperti kotlinx.coroutines, mendukung paralelisme untuk menjalankan tugas-tugas secara konkuren di beberapa core CPU sesuai kebutuhan.

## Kotlin Coroutines
Coroutines sering disebut sebagai "thread yang ringan" karena mereka lebih efisien dalam penggunaan sumber daya daripada thread tradisional, memungkinkan jutaan coroutines berjalan pada beberapa thread. Meskipun memiliki life cycle yang mirip dengan thread, coroutines lebih mudah diterapkan dan lebih powerful, terutama di lingkungan mobile di mana kinerja sangat krusial. Meskipun dijalankan di dalam thread, coroutines tidak terikat dengan thread tersebut, memungkinkan pengguna untuk mengatur jalannya coroutine, termasuk menentukan apakah akan dijalankan dalam thread tertentu atau ditangguhkan untuk dilanjutkan pada thread lainnya. Berikut ini contoh dari penggunaan coroutines.
```kotlin
import kotlinx.coroutines.*
 
fun main() = runBlocking{
    val coroutineLaunch = launch {
        delay(1000L)
        println("Executing Coroutines!")
    }
    println("Hello, Coroutine World!")
    delay(2000L)
}
```

## Coroutines Builder
Coroutine builder adalah fungsi yang digunakan untuk membuat coroutine dalam Kotlin. Ada beberapa coroutine builder yang umum digunakan, termasuk launch, async, dan runBlocking.
* **launch**: Digunakan untuk memulai coroutine yang tidak mengembalikan nilai (void). Coroutine yang dibuat dengan launch akan berjalan secara asynchronous.
* **async**: Digunakan untuk memulai coroutine yang mengembalikan nilai. Coroutine yang dibuat dengan async akan mengembalikan objek Deferred, yang dapat digunakan untuk mendapatkan nilai hasil coroutine di masa depan.
* **runBlocking**: Digunakan untuk membuat coroutine dari kode yang tidak bersifat suspending. Biasanya digunakan dalam fungsi main() untuk membuat coroutine tanpa harus menggunakan GlobalScope.


Misalkan kita ingin membuat sebuah aplikasi sederhana yang melakukan dua tugas secara asynchronous: mencetak "Hello" setelah 1 detik, dan mencetak "World" setelah 2 detik. Berikut contohnya:
```kotlin
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis
 
fun main() = runBlocking {
    val timeOne = measureTimeMillis {
        val capital = getCapital()
        val income = getIncome()
        println("Your profit is ${income - capital}")
    }
 
    val timeTwo = measureTimeMillis {
        val capital = async { getCapital() }
        val income = async { getIncome() }
        println("Your profit is ${income.await() - capital.await()}")
    }
 
    println("Completed in $timeOne ms vs $timeTwo ms")
 
}


suspend fun getCapital(): Int {
    delay(1000L)
    return 50000
}
 
suspend fun getIncome(): Int {
    delay(1000L)
    return 75000
}
```


## Job dan Deferred
### Job 
Job dalam Kotlin merupakan hasil dari perintah asynchronous yang dijalankan, merepresentasikan coroutine yang sebenarnya, dengan tiga properti utama: isActive, isCompleted, dan isCancelled, yang merefleksikan status dari job tersebut. Siklus hidup job meliputi state New, Active, Completed, Cancelling, dan Cancelled, yang menunjukkan status job mulai dari inisialisasi hingga selesai atau dibatalkan.

Berikut adalah contoh sederhana menjalankan sebuah job dalam Kotlin menggunakan coroutines:
```kotlin
import kotlinx.coroutines.*

fun main() = runBlocking {
    val task = launch(start = CoroutineStart.LAZY) {
        delay(1000L)
        println("Starting a new task!")
    }
 
    task.start()
    println("Performing other task")
}
```
Berikut adalah contoh cara membatalkan sebuah job dalam Kotlin menggunakan coroutines:
```kotlin
import kotlinx.coroutines.*
import kotlin.coroutines.*

@InternalCoroutinesApi
fun main() = runBlocking {
    val task = launch {
        delay(5000)
        println("Start new task!")
    }
 
    delay(2000)
    task.cancel(cause = CancellationException("Time is up!"))
    println("Cancelling task...")
    if (task.isCancelled){
        println("Task is cancelled because ${task.getCancellationException().message}")
    }
}
```

## Deferred
Deferred adalah nilai tangguhan yang menunda hasil atau pengecualian dari proses coroutines, biasanya dihasilkan oleh fungsi async. Meskipun mirip dengan job dalam life cycle, deferred memiliki kemampuan untuk menghasilkan nilai baik saat proses berhasil maupun gagal, dapat diakses dengan await dan pengecualian dapat diakses dengan getCompletionExceptionOrNull. Deferred juga dapat dimulai secara lazy menggunakan parameter opsional seperti CoroutineStart.LAZY.

## Coroutines Dispatcher
Coroutine Dispatcher adalah mekanisme dalam Kotlin untuk menentukan thread mana yang akan digunakan untuk menjalankan dan melanjutkan coroutines. Terdapat beberapa tipe Dispatcher yang tersedia:
* **Dispatchers.Default**: Dispatcher dasar yang digunakan jika tidak ada dispatcher lain yang ditentukan, menggunakan kumpulan thread pada JVM dengan jumlah maksimal yang sama dengan jumlah core CPU.
* **Dispatchers.IO**: Dispatcher untuk operasi I/O yang memungkinkan pembongkaran pemblokiran, menggunakan kumpulan thread yang dibuat berdasarkan permintaan.
* **Dispatchers.Unconfined**: Dispatcher yang menjalankan coroutines pada thread yang sedang berjalan sampai mencapai titik penangguhan, dan dilanjutkan pada thread yang memanggil fungsi penangguhan.
* **Single Thread Context**: Dispatcher yang menjamin bahwa coroutines akan dijalankan pada thread yang ditentukan secara eksplisit.
* **Thread Pool**: Dispatcher dengan kumpulan thread yang tersedia, dan proses distribusi bebannya ditentukan oleh runtime.

## Channels
Channels adalah nilai deferred yang menyediakan cara mudah untuk mentransfer nilai tunggal antara coroutines. Mereka mirip dengan BlockingQueue, tetapi alih-alih memblokir sebuah thread, channels menangguhkan sebuah coroutine yang lebih ringan. Channels memungkinkan komunikasi yang aman antar kode concurrent dengan mengirim dan menerima pesan tanpa harus peduli di thread mana coroutine berjalan.

<br/>
