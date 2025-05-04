# State Pada Jetpack Compose

State adalah komponen yang menyimpan nilai yang dapat berubah dari waktu ke waktu, sehingga memungkinkan UI juga ikut berubah. Dengan cara ini, aplikasi bisa menjadi dinamis, tidak lagi statis.

Ada banyak contoh penerapan dari State, seperti:

* Mengaktifkan button ketika input yang diberikan valid;
* Menentukan radio button yang dipilih;
* Menampilkan dropdown saat action menu dipilih;
* Menampilkan loading ketika memuat data;
* Menentukan posisi scroll pada list; dan
* Menentukan halaman yang tampil ketika menu dipilih.

---

## Teori Mengatur State pada Compose

### mutableStateOf, remember, & rememberSaveable

Perlu diingat, tanpa State, UI tidak akan bisa berubah, bahkan untuk hal sederhana sekalipun. Contoh paling umum adalah TextField untuk input seperti berikut:

```kotlin
@Composable
fun FormInput() {
    OutlinedTextField(
        value = "",
        onValueChange = {},
    )
}
```

Jika Anda jalankan dan mencoba mengisinya, teks tidak akan berubah karena belum ada state yang mengelola nilai input tersebut.

![s1](/week-07/img/s1.jpeg)

Nah, untuk memberitahu sistem agar mengenali perubahan, gunakanlah `mutableStateOf` yang dikombinasikan dengan `remember`.

```kotlin
@Composable
fun FormInput() {
    val name = remember { mutableStateOf("") } //state
    OutlinedTextField(
        value = name.value, //display state
        onValueChange = { newName -> //event
            name.value = newName //update state
        },
        label = { Text("Nama") },
        modifier = Modifier.padding(8.dp)
    )
}
```

`mutableStateOf` berfungsi untuk melacak nilai yang berubah. Ia akan mengubah nilainya menjadi `MutableState`, yang merupakan tipe observable di Compose. Dengan begitu, Compose akan otomatis membaca nilai tersebut dan melakukan pembaruan UI ketika data berubah.

Sementara itu, `remember` berfungsi untuk menyimpan nilai tersebut ke dalam memori agar datanya tidak hilang ketika terjadi recomposition.

Kalau diperhatikan, untuk mengambil nilai dari `remember`, kita perlu menulis `.value` setiap kali ingin membaca atau mengubahnya. Supaya kode lebih ringkas, Anda bisa menuliskan seperti berikut:

```kotlin
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun FormInput() {
    var name by remember { mutableStateOf("") } //state
    OutlinedTextField(
        value = name, //display state
        onValueChange = { newName -> //event
            name = newName //update state
        },
        label = { Text("Nama") },
        modifier = Modifier.padding(8.dp)
    )
}
```

`by remember` di sini menggunakan property delegation untuk menangani proses `getValue` dan `setValue` secara otomatis. Jangan lupa tambahkan import untuk kedua fungsi tersebut.

Alhasil, sekarang TextField sudah bisa berfungsi normal seperti ini.

![s2](/week-07/img/s2.gif)

Selain `remember`, ada juga `rememberSaveable`. Bedanya, `rememberSaveable` akan menyimpan state ke dalam `Bundle` sehingga nilainya tetap aman meskipun terjadi perubahan konfigurasi, seperti saat aplikasi dirotasi.&#x20;

Berikut perbandingannya:

| Dengan rememberSaveable               | Tanpa rememberSaveable            |
| ------------------------------------- | --------------------------------- |
| ![s3](/week-07/img/s3.gif)|![s4](/week-07/img/s4.gif)|

---

## Teori State Hoisting pada Compose

State Hoisting adalah pola untuk memindahkan state ke parent Composable-nya, sehingga sebuah Composable menjadi stateless. Konsep ini penting dalam Jetpack Compose agar kode lebih fleksibel dan mudah diatur.

Namun sebelum membahas lebih jauh tentang State Hoisting, Anda perlu tahu bahwa ada dua jenis Composable Function, yaitu Stateful dan Stateless.

### Stateful dan Stateless

**Stateful** adalah Composable yang menyimpan state di dalamnya. Biasanya, ini ditandai dengan penggunaan `remember`. Stateful composables cocok digunakan ketika parent-nya tidak perlu mengontrol child-nya, sehingga child dapat berjalan mandiri. Namun, jenis ini kurang fleksibel karena sulit untuk dites dan kurang reusable.

Sebaliknya, **Stateless** adalah Composable yang tidak menyimpan state di dalamnya. Jenis ini lebih fleksibel karena logikanya diatur dari luar, sehingga lebih mudah untuk diuji dan digunakan kembali.

Lalu, bagaimana cara mengubah Composable dari stateful menjadi stateless? Jawabannya adalah dengan menggunakan **State Hoisting**. Mari kita lihat contoh kode berikut:

```kotlin
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        var count by rememberSaveable { mutableIntStateOf(0) }
        Text("Button clicked $count times:")
        Button(onClick = { count++ }) {
            Text("Click me!")
        }
    }
}
```

Kode di atas adalah contoh Stateful Composable yang menampilkan counter (penghitung angka) berdasarkan jumlah klik.

Untuk mengubahnya menjadi Stateless dengan State Hoisting, kita pindahkan variabel state ke parent dan gunakan dua parameter berikut di dalam composable-nya:

* `value: T`, yaitu nilai state yang akan ditampilkan.
* `onEvent: (T) -> Unit`, yaitu lambda yang menampung event perubahan.

Berikut implementasinya:

```kotlin
@Composable
fun StatefulCounter(modifier: Modifier = Modifier) {
    var count by rememberSaveable { mutableIntStateOf(0) }
    StatelessCounter(
        count = count,
        onClick = { count++ },
        modifier = modifier,
    )
}

@Composable
fun StatelessCounter(
    count: Int,           //state
    onClick : () -> Unit, //event
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(16.dp)
    ) {
        Text("Button clicked $count times:")
        Button(onClick = { onClick() }) {
            Text("Click me!")
        }
    }
}
```

Berikut Hasilnya:

<img src="/week-07/img/s5.gif" alt="s5" width="200"/>

Dengan memindahkan state ke parent, kita jadi lebih leluasa mengontrol logikanya dari luar. Misalnya, Anda bisa mengubah aksi `count++` menjadi `count *= 2` agar setiap klik menghasilkan dobel poin.
