# Work Manager

Pekerjaan akan bersifat persisten jika tetap dijadwalkan melalui proses mulai ulang aplikasi dan sistem memulai ulang komputer. WorkManager adalah solusi yang direkomendasikan untuk pekerjaan persisten. Karena sebagian besar pemrosesan latar belakang paling baik dilakukan melalui kerja persisten, Oleh karena itu, WorkManager juga merupakan API utama yang direkomendasikan untuk latar belakang diproses secara umum.

### Jenis pekerjaan persisten
WorkManager menangani tiga jenis pekerjaan persisten:

- Langsung: Tugas yang harus segera dimulai dan diselesaikan. Mungkin diprioritaskan.
- Berjalan Lama: Tugas yang mungkin berjalan lebih lama, kemungkinan lebih lama dari 10 menit.
- Dapat ditangguhkan: Tugas terjadwal yang dimulai di lain waktu dan dapat dijalankan secara berkala.

Gambar ini menguraikan bagaimana berbagai jenis pekerjaan persisten terkait satu sama lain.
![Jenis pekerjaan persisten](https://developer.android.com/static/images/guide/background/workmanager_main.svg?hl=id)

Demikian pula, tabel berikut menguraikan berbagai jenis pekerjaan.

| Jenis | Periodisitas | Cara mengakses |
| -- | -- | -- |
| Segera | Satu kali | OneTimeWorkRequest dan Worker. Untuk pekerjaan yang diprioritaskan, panggil setExpedited() di OneTimeWorkRequest. |
| Berjalan Lama | Satu kali atau berkala | WorkRequest apa pun atau Worker. Panggil setForeground() di Pekerja untuk menangani notifikasi. |
| Dapat ditangguhkan | Satu kali atau berkala | PeriodicWorkRequest dan Worker. |

### Fitur WorkManager
Selain menyediakan API yang lebih sederhana dan konsisten, WorkManager memiliki sejumlah manfaat utama lainnya:

#### Batasan pekerjaan
Tentukan dengan jelas kondisi optimal untuk pekerjaan yang akan dijalankan menggunakan Batasan pekerjaan. Misalnya, jalankan hanya ketika perangkat berada di jaringan tidak berbayar, saat perangkat tidak ada aktivitas, atau saat baterai perangkat mencukupi.

#### Penjadwalan yang andal
WorkManager memungkinkan Anda menjadwalkan pekerjaan untuk menjalankan satu kali atau berulang kali menggunakan jendela penjadwalan fleksibel. Pekerjaan dapat diberi tag dan diberi nama, sehingga Anda dapat menjadwalkan pekerjaan yang unik dan dapat diganti, serta memantau atau membatalkan grup pekerjaan sekaligus.

Pekerjaan terjadwal disimpan dalam database SQLite yang dikelola secara internal dan WorkManager memastikan bahwa pekerjaan ini tetap ada dan dijadwalkan ulang di seluruh reboot perangkat.

Selain itu, WorkManager mematuhi fitur penghemat daya dan praktik terbaik seperti mode Istirahatkan, sehingga Anda tidak perlu mengkhawatirkannya.

#### Tugas yang diprioritaskan
Anda dapat menggunakan WorkManager untuk menjadwalkan pekerjaan langsung untuk eksekusi di latar belakang. Anda harus menggunakan Tugas yang diprioritaskan untuk tugas yang penting bagi pengguna dan yang selesai dalam beberapa menit.

#### Kebijakan percobaan ulang fleksibel
Terkadang pekerjaan gagal. WorkManager menawarkan kebijakan percobaan ulang fleksibel, termasuk kebijakan backoff eksponensial yang dapat dikonfigurasi.

#### Perantaian pekerjaan
Untuk pekerjaan terkait yang kompleks, ikat semua pekerjaan individu menggunakan antarmuka intuitif yang memungkinkan Anda mengontrol bagian mana yang berjalan secara berurutan dan yang berjalan secara paralel.

```kotlin
val continuation = WorkManager.getInstance(context)
    .beginUniqueWork(
        Constants.IMAGE_MANIPULATION_WORK_NAME,
        ExistingWorkPolicy.REPLACE,
        OneTimeWorkRequest.from(CleanupWorker::class.java)
    ).then(OneTimeWorkRequest.from(WaterColorFilterWorker::class.java))
    .then(OneTimeWorkRequest.from(GrayScaleFilterWorker::class.java))
    .then(OneTimeWorkRequest.from(BlurEffectFilterWorker::class.java))
    .then(
        if (save) {
            workRequest<SaveImageToGalleryWorker>(tag = Constants.TAG_OUTPUT)
        } else /* upload */ {
            workRequest<UploadWorker>(tag = Constants.TAG_OUTPUT)
        }
    )

```

Untuk setiap tugas kerja, Anda dapat menentukan data input dan output untuk tugas tersebut. Saat merantai pekerjaan bersama, WorkManager secara otomatis meneruskan data output dari satu tugas kerja ke tugas berikutnya.

#### Interoperabilitas threading bawaan
WorkManager terintegrasi dengan lancar dengan Coroutine dan RxJava dan memberikan fleksibilitas untuk melakukan plugin API asinkron Anda sendiri.

### Menggunakan WorkManager untuk pekerjaan yang andal
WorkManager dimaksudkan untuk pekerjaan yang perlu berjalan secara andal meskipun pengguna mematikan layar, aplikasi keluar, atau perangkat dimulai ulang. Contoh:

Mengirim log atau analisis ke layanan backend.
Menyinkronkan data aplikasi dengan server secara berkala.
WorkManager tidak dimaksudkan untuk pekerjaan latar belakang dalam proses yang dapat dihentikan dengan aman jika proses aplikasi berhenti. Solusi ini juga bukan solusi umum untuk semua pekerjaan yang memerlukan eksekusi langsung. Tinjau panduan pemrosesan di latar belakang untuk menemukan solusi mana yang sesuai dengan kebutuhan Anda.

### Hubungan dengan API lainnya
Meskipun coroutine adalah solusi yang direkomendasikan untuk kasus penggunaan tertentu, Anda tidak boleh menggunakannya untuk pekerjaan persisten. Penting untuk diperhatikan bahwa coroutine adalah framework konkurensi, sedangkan WorkManager adalah library untuk Anda. Anda juga harus menggunakan AlarmManager untuk jam atau kalender saja.

| API | Direkomendasikan untuk | Hubungan dengan WorkManager |
| -- | -- | -- |
| Coroutine | Semua pekerjaan asinkron yang tidak harus persisten. | Coroutine adalah cara standar untuk keluar dari thread utama di Kotlin. Namun, coroutine akan meninggalkan memori setelah aplikasi ditutup. Untuk pekerjaan persisten, gunakan WorkManager. |
| AlarmManager | Hanya alarm. | Tidak seperti WorkManager, AlarmManager mengaktifkan perangkat dari mode Istirahatkan. Oleh karena itu, ini tidak efisien dalam hal pengelolaan daya dan resource. Hanya gunakan untuk alarm atau notifikasi akurat seperti acara kalender — bukan pekerjaan latar belakang. |

### Mengganti API yang tidak digunakan lagi
WorkManager API adalah pengganti yang direkomendasikan untuk semua Android versi sebelumnya API penjadwalan latar belakang, termasuk FirebaseJobDispatcher, GcmNetworkManager, dan JobScheduler.