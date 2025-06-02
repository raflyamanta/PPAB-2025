# Overview

## Local Data Persistent

Pengembangan aplikasi pada platform manapun tidak luput dari proses penyimpanan dan manipulasi data. Pada perangkat Android, terdapat beragam mekanisme penyimpanan yang bergantung pada kebutuhan aplikasi.

Pada umumnya penyimpanan dan pengolahan data di Android terbagi menjadi:

### onSavedInstanceState

Ini adalah penyimpanan data terkecil di dalam Android. Ia hanya menyimpan data ketika aplikasi dibuka saja, ketika aplikasi ditutup datanya hilang. Data ini biasanya digunakan untuk menyimpan data sementara, seperti data yang diambil dari server atau data yang diinputkan pengguna. Data ini akan hilang ketika aplikasi ditutup atau di-restart.

### App-Spesific Storage

Jenis penyimpanan ini menggunakan mekanisme pembuatan berkas yang secara umum dilakukan pada operasi berkas pemrograman berbasis Java. Setiap berkas yang tercipta akan secara otomatis tersimpan pada internal storage atau external storage dan hanya bisa diakses oleh aplikasi yang membuatnya. Ketika pengguna mencopot aplikasi, maka semua berkas yang dibuatnya akan dihapus. Untuk saat ini, tidak perlu permission apa pun untuk menggunakan penyimpan ini, Anda hanya perlu menggunakan metode getFileDir() atau getExternalFileDir().

### Shared Storage

Yang dimaksud dengan shared storage di sini adalah jenis penyimpanan yang ditujukan supaya bisa diakses oleh aplikasi lain, contohnya seperti menyimpan media berupa foto, audio, ataupun video. Jenis penyimpanan ini tidak akan hilang walaupun aplikasi sudah di-uninstall. Untuk memanfaatkan penyimpanan ini, Anda membutuhkan permission dan MediaStore API. Oleh karena data yang disimpan di shared storage bisa diakses oleh aplikasi lain, maka Anda harus memperhatikan keamanan data yang disimpan.

### SharedPreferences

Ini adalah cara sederhana untuk menyimpan data primitif dalam bentuk pasangan key-value. Ini sangat berguna untuk menyimpan data seperti preferensi pengguna atau status sesi. Data yang disimpan dalam SharedPreferences akan tetap ada di seluruh sesi aplikasi, bahkan setelah aplikasi ditutup dan dibuka kembali. Data ini hanya akan hilang jika aplikasi di-uninstall atau data dihapus secara manual melalui pengaturan aplikasi.

### DataStore

Media penyimpanan jenis ini akan memudahkan untuk menyimpan data dengan tipe data primitif seperti boolean, int, long, atau string yang tidak berelasi satu sama lain. Konsep yang digunakan pada jenis ini adalah pasangan antara key–value. Biasanya penyimpanan jenis ini digunakan untuk menyimpan pengaturan aplikasi, seperti ukuran huruf, tema, atau notifikasi. Data ini akan selalu ada bahkan ketika aplikasi telah ditutup karena disimpan di dalam file dalam internal storage dari aplikasi. Hanya akan hilang ketika aplikasi di-uninstall atau data dihapus secara manual. Perbedaan DataStore dengan SharedPreferences adalah DataStore menggunakan konsep coroutine dan flow, sehingga lebih efisien, lebih modern, dan lebih robust.

### SQLiteDatabase

Ini adalah database open source yang ringan dan biasa digunakan di Android. Anda bisa menyimpan data dalam bentuk tabel-tabel berelasi seperti yang dilakukan pada platform lain. Database semacam ini dibutuhkan untuk menyimpan data yang kompleks, seperti catatan keuangan atau tugas harian. Selain itu, ia juga sering digunakan untuk menyimpan sementara data dari server, sehingga aplikasi tetap bisa dijalankan walaupun offline dan menjadi lebih hemat kuota.

Tidak seperti pengaturan database di web maupun desktop, Anda tidak perlu mengatur koneksi dan autentikasi untuk membuat database di Android. SQlite juga tidak membutuhkan memori yang besar, karena itulah hampir semua penyimpanan di handphone menggunakan SQLite.

### Room Database

Room merupakan library ORM (Object Relational Mapping) yang termasuk dalam Android Jetpack. Room menyediakan lapisan abstraksi di atas SQLite. Ini memungkinkan kita untuk mengakses database dengan mudah dan cepat. Dengan menggunakan Room, Anda tidak perlu menuliskan query untuk memasukkan dan menghapus database secara manual. Hal ini karena Room menggunakan mekanisme DAO (Data Access Object), di mana ia mengubah objek database menjadi objek Kotlin/Java. Dengan begitu, apabila terdapat eror akan langsung terdeteksi saat compile time, bukan saat runtime. Selain itu, Room juga terintegrasi dengan komponen Jetpack data yang lainnya, yakni LiveData. Sehingga, Anda dapat membaca data pada database secara real-time.

### Network

Sudah hal yang lumrah bagi aplikasi Android untuk mengakses data melalui jaringan Internet. Ini adalah mekanisme penyimpanan terpusat yang memudahkan pengguna untuk mendapatkan data yang real time dari server. Dengan menyimpan data pada server, Anda dapat mengakses data dari device yang berbeda dengan mudah. Anda tidak perlu khawatir jika aplikasi dihapus atau device hilang, karena semua data ada di server. Kita sudah pernah pernah mempelajari cara mengirim dan menerima data dari server pada modul sebelumnya.

### Perbedaan Penyimpanan Data

Untuk melihat perbedaan dari masing-masing penyimpanan data, lihatlah tabel berikut:

| Jenis Penyimpanan    | Tipe Data                                  | Lama Penyimpanan                           |
| -------------------- | ------------------------------------------ | ------------------------------------------ |
| onSavedInstanceState | Key-value                                  | Hanya ketika aplikasi dibuka               |
| App-Specific Storage | Penyimpanan media privat                   | Sampai aplikasi di-uninstall/clear data    |
| Shared Storage       | Penyimpanan media publik                   | Tetap ada walaupun di-uninstall/clear data |
| SharedPreferences    | Key-value                                  | Sampai aplikasi di-uninstall/clear data    |
| DataStore            | Key-value                                  | Sampai aplikasi di-uninstall/clear data    |
| SQLite Database      | Tabel data lokal                           | Sampai aplikasi di-uninstall/clear data    |
| Room Database        | Tabel data lokal berupa ORM                | Sampai aplikasi di-uninstall/clear data    |
| Network/Server       | Tabel data yang bisa diakses banyak device | Selama server aktif                        |

## Referensi

1. https://developer.android.com/guide/topics/data/data-storage
2. https://developer.android.com/training/data-storage
3. https://developer.android.com/topic/libraries/architecture/room
4. https://developer.android.com/topic/libraries/architecture/datastore
5. https://developer.android.com/training/data-storage/sqlite
6. https://developer.android.com/training/data-storage/app-specific
7. https://developer.android.com/training/data-storage/shared
8. https://developer.android.com/training/basics/network-ops/connecting

**[>> Materi Sesudahnya (File Storage) >>](1-FileStorage.md)**
