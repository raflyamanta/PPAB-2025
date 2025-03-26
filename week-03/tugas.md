# Tugas  Praktikum PAB (Week 3)
### Buatlah **sebuah aplikasi android** dengan bahasa Kotlin dimana aplikasi tersebut wajib mengimplementasikan fitur-fitur berikut ini.

    1. 2 Activity, sudah termasuk Main Activity
    2. Explicit Intent
    3. Implicit Intent

    Mendapatkan nilai tambahan jika mengimplementasikan >1 Implicit Intent.

### Berikut kerangka user interface yang bisa digunakan untuk referensi

1. Ketika **button Go To Profile** pada Halaman Utama ditekan, maka akan mengirimkan data dengan **intent** ke halaman Profile. Jadi, data yang dikirim ke halaman profile adalah **NIM, nama lengkap, jurusan, angkatan, dan deskripsi diri**. Ini penerapan untuk Explicit Intent.
2. Ketika **button Share** ditekan, maka akan meneruskan informasi **NIM, nama lengkap, jurusan, angkatan, dan deskripsi diri** ke aplikasi default berdasarkan **action** dari Implicit Intent yang digunakan.
3. Ketika **button Go To My Github** ditekan, maka akan membuka aplikasi web browser default dan akan langsung mengarahkan ke akun Github yang telah ditentukan. Fitur ini bersifat **opsional** dan akan **mendapatkan nilai tambahan** jika diimplementasikan.


    ![alt text](kerangka-interface.png)

### Contoh Final Aplikasi
[KLIK](https://drive.google.com/file/d/1G-5UYgLbITo60RKHTyP0ycap_12mn0LD/view?usp=drive_link)

### Ketentuan Source Code Aplikasi
1. Menggunakan Android Studio
2. Menggunakan bahasa pemrograman Kotlin
3. Asisten akan menggunakan emulator dengan SDK level 30, maka pastikan saat build aplikasi, **gunakanlah Minimum SDK <= 30**

### Detail Laporan Praktikum
#### Laporan berisi screenshot source code penggunaan materi praktikum, tampilan user interface beserta penjelasan, dan diakhiri dengan kesimpulan. 
    
Tugas dikumpulkan dalam bentuk **.ZIP** dengan format nama **PPAB-03_NIM_Nama.zip yang berisi source code dari aplikasi** dan **.PDF dengan format nama PPAB-03_NIM_Nama.pdf yang berisi laporan praktikum**. Perlu diingat bahwa **file .zip tidak berisikan file pdf, murni berisi source code saja** dan gunakan **cara .zip sesuai yang diajarkan oleh asisten**.

    PPAB-XX_NIM_Nama.zip
    PPAB-XX_NIM_Nama.pdf
    
    xx = week praktikum

[Template Laporan Praktikum](https://docs.google.com/document/d/1zh9XS41ARJFT3TTgXNUspGXTsvpG097l/edit?usp=drive_link&ouid=102536223942160379481&rtpof=true&sd=true)

[Tutorial Export Zip Android Studio](https://youtu.be/4r2czHHh3a0?si=J_6iTJ2qjnmQhrza)

### Penilaian didasarkan pada: 
    1. Aplikasi Bebas dari error ketika dijalankan secara normal (10%)
    2. Relevansi source code dengan instruksi di atas (50%) 
    3. Kompleksitas Program yang dibuat (30%)
    4. Kreativitas masing-masing individu (10%). 
