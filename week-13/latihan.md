# Latihan Work Manager

### Tambahkan dependency 
```kotlin
dependencies {
    val work_version = "2.10.1"

    // (Java only)
    implementation("androidx.work:work-runtime:$work_version")

    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$work_version")

    // optional - RxJava2 support
    implementation("androidx.work:work-rxjava2:$work_version")

    // optional - GCMNetworkManager support
    implementation("androidx.work:work-gcm:$work_version")

    // optional - Test helpers
    androidTestImplementation("androidx.work:work-testing:$work_version")

    // optional - Multiprocess support
    implementation("androidx.work:work-multiprocess:$work_version")
}
```

### Membuat worker
Pekerjaan ditentukan menggunakan class Worker. Metode doWork() berjalan secara asinkron di thread latar belakang yang disediakan oleh WorkManager.
Untuk membuat pekerjaan, extend class Worker dan override metode doWork().
```kotlin
class MyWorker(appContext: Context, params: WorkerParameters) : Worker(appContext, params) {
    override fun doWork(): Result {
        Log.d("MyWorker", "Halo dari WorkManager!")
        return Result.success()
    }
}
```
Result yang ditampilkan dari doWork() memberi tahu layanan WorkManager apakah pekerjaan berhasil dan, jika terjadi kegagalan, apakah pekerjaan tersebut harus dicoba ulang atau tidak.
- Result.success(): Pekerjaan berhasil diselesaikan.
- Result.failure(): Pekerjaan gagal.
- Result.retry(): Pekerjaan gagal dan harus dicoba di lain waktu sesuai dengan kebijakan coba lagi.

### Membuat WorkRequest
Setelah membuat pekerjaan, pekerjaan Anda harus dijadwalkan dengan layanan WorkManager agar dapat berjalan. WorkManager menawarkan banyak fleksibilitas dalam cara Anda menjadwalkan pekerjaan. Anda dapat menjadwalkannya agar berjalan secara berkala selama interval waktu tertentu, atau Anda dapat menjadwalkannya untuk berjalan satu kali saja.

Apa pun pilihan untuk menjadwalkan pekerjaan, Anda akan selalu menggunakan WorkRequest. Sementara Worker menentukan unit pekerjaan, WorkRequest (dan subkelasnya) menentukan bagaimana dan kapan harus dijalankan. Jika anda ingin suatu pekerjaan untuk dilakukan sekali saja, Anda dapat menggunakan OneTimeWorkRequest, seperti yang ditunjukkan pada contoh berikut.
```kotlin
val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
```

### Mengirim WorkRequest ke sistem
Terakhir, Anda harus mengirimkan WorkRequest ke WorkManager menggunakan metode enqueue().
```kotlin
WorkManager.getInstance(context).enqueue(workRequest)
```
Waktu pasti eksekusi pekerja bergantung pada batasan (constraints) yang digunakan dalam WorkRequest dan pada pengoptimalan sistem. WorkManager dirancang untuk memberikan perilaku terbaik berdasarkan batasan ini.

### Menambahkan Constraints
Constraints memastikan bahwa pekerjaan ditangguhkan hingga kondisi optimal terpenuhi. Batasan berikut tersedia untuk WorkManager:
| Constraint | Deskripsi |
| -- | -- |
| NetworkType | Batasi jenis jaringan yang diperlukan agar pekerjaan Anda dapat berjalan. Misalnya, Wi-Fi (UNMETERED).|
| BatteryNotLow | Bila disetel ke benar (true), pekerjaan Anda tidak akan berjalan jika perangkat dalam mode baterai lemah.|
| RequiresCharging | Jika disetel ke benar (true), pekerjaan Anda hanya akan berjalan saat perangkat sedang diisi dayanya.|
| DeviceIdle | Jika disetel ke benar (true), tindakan ini mengharuskan perangkat pengguna tidak beraktivitas sebelum pekerjaan berjalan. Tindakan ini berguna untuk menjalankan operasi batch yang mungkin berdampak negatif pada performa aplikasi lain yang berjalan secara aktif pada perangkat pengguna.|
| StorageNotLow | Jika disetel ke benar (true), pekerjaan Anda tidak akan berjalan jika ruang penyimpanan pengguna di perangkat terlalu rendah. |

Berikut cara menambahkan constraint:
```kotlin
val constraints = Constraints.Builder()
    .setRequiresCharging(true)
    .setRequiredNetworkType(NetworkType.CONNECTED)
    .build()

val constrainedRequest = OneTimeWorkRequestBuilder<MyWorker>()
    .setConstraints(constraints)
    .build()

WorkManager.getInstance(context).enqueue(constrainedRequest)
```

### Menjadwalkan pekerjaan berkala
Terkadang, aplikasi Anda mungkin memerlukan pekerjaan tertentu untuk dijalankan secara berkala. Misalnya, Anda mungkin perlu mencadangkan data, mendownload konten baru di aplikasi, atau mengupload log ke server secara berkala.

Berikut adalah cara menggunakan PeriodicWorkRequest untuk membuat objek WorkRequest yang dieksekusi secara berkala. Misalnya, setiap 15 menit:
```kotlin
val periodicRequest = PeriodicWorkRequestBuilder<MyWorker>(
    15, TimeUnit.MINUTES
).build()

WorkManager.getInstance(context).enqueueUniquePeriodicWork(
    "MyPeriodicWork",
    ExistingPeriodicWorkPolicy.KEEP,
    periodicRequest
)
```
UniquePeriodicWork → mencegah duplikat job
KEEP → tetap gunakan job yang sudah ada jika belum selesai

### Chaining 
Misal kita punya 3 worker: Step1Worker, Step2Worker, Step3Worker. Mereka akan dijalankan secara berurutan.
```kotlin
val step1 = OneTimeWorkRequestBuilder<Step1Worker>().build()
val step2 = OneTimeWorkRequestBuilder<Step2Worker>().build()
val step3 = OneTimeWorkRequestBuilder<Step3Worker>().build()

WorkManager.getInstance(context)
    .beginWith(step1)
    .then(step2)
    .then(step3)
    .enqueue()
```
Catatan: Worker selanjutnya hanya akan jalan jika worker sebelumnya Result.success(). Chaining juga hanya dapat dilakukan dengan OneTimeRequest.