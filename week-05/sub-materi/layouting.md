
#  Layouting dengan Jetpack Compose: ConstraintLayout

Jetpack Compose menyediakan `ConstraintLayout` untuk membuat layout yang fleksibel dan kompleks dengan pendekatan deklaratif.

---

#  Menambahkan Dependency

Tambahkan dependency berikut pada file `build.gradle(:app)`:

```kotlin
dependencies {
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
}
```

---

###  Dasar ConstraintLayout

`ConstraintLayout` memungkinkan kita mengatur posisi setiap elemen secara relatif terhadap elemen lain atau `parent`. Ini sangat berguna untuk layout kompleks yang tidak bisa ditangani dengan `Column` dan `Row`.

Konsep utamanya:

- `createRefs()` digunakan untuk mendapatkan referensi ke setiap komponen.
- `constrainAs()` dipakai untuk menetapkan constraint ke setiap elemen.
- Kita bisa menetapkan hubungan seperti `top.linkTo()`, `bottom.linkTo()`, `start.linkTo()`, dan `end.linkTo()`.

Contoh dasar:

```kotlin
@Composable
fun BasicConstraintLayout() {
    ConstraintLayout(
        modifier = Modifier.fillMaxSize()
    ) {
        val (button, text) = createRefs()

        Button(
            onClick = { /* aksi */ },
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text("Klik Saya")
        }

        Text(
            text = "Hello ConstraintLayout",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(button.bottom, margin = 16.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}
```
`ConstraintLayout` adalah *layout container* yang memungkinkan kita menempatkan komponen UI berdasarkan **relasi atau constraint** terhadap elemen lain (baik terhadap *parent* maupun *sibling*).  
Layout ini sangat fleksibel dan cocok untuk situasi kompleks di mana `Column`, `Row`, atau `Box` tidak cukup.

---

### 1.  `createRefs()`

Fungsi `createRefs()` digunakan untuk membuat *referensi unik* terhadap setiap elemen yang akan dikontrol di dalam `ConstraintLayout`.

Contoh:

```kotlin
val (button, text) = createRefs()
```

Ini artinya kamu membuat dua referensi bernama `button` dan `text`. Referensi ini digunakan sebagai "penanda" agar kamu bisa mengatur posisi mereka menggunakan `constrainAs()`.

Jika elemen banyak, kamu juga bisa gunakan:

```kotlin
val refs = createRefs()
val button = refs.createRef()
val text = refs.createRef()
```

---

### 2.  `constrainAs()`

Setiap elemen dalam `ConstraintLayout` harus diberikan constraint menggunakan `constrainAs()`.

Sintaks:

```kotlin
Modifier.constrainAs(ref) {
    // constraint di sini
}
```

Di dalam block `constrainAs`, kamu menetapkan posisi elemen tersebut **relatif terhadap elemen lain** atau terhadap **parent**.

Misalnya:

```kotlin
top.linkTo(parent.top)
start.linkTo(parent.start)
```

Artinya elemen tersebut berada di pojok kiri atas parent.

Contoh lengkap:

```kotlin
Button(
    onClick = {},
    modifier = Modifier.constrainAs(button) {
        top.linkTo(parent.top, margin = 16.dp)
        start.linkTo(parent.start)
        end.linkTo(parent.end)
    }
) {
    Text("Klik Saya")
}
```

Penjelasan:
- `button` diposisikan 16dp dari atas parent
- berada di tengah horizontal (karena `start` dan `end` di-link ke parent)

---

### 3.  Constraint Link: `linkTo()`

Berikut adalah beberapa fungsi utama dalam constraint:

| Fungsi             | Deskripsi |
|--------------------|-----------|
| `top.linkTo(...)`    | Menetapkan sisi atas elemen agar terkait dengan elemen lain |
| `bottom.linkTo(...)` | Menetapkan sisi bawah elemen |
| `start.linkTo(...)`  | Menetapkan sisi kiri elemen (kiri dalam LTR) |
| `end.linkTo(...)`    | Menetapkan sisi kanan elemen |
| `centerHorizontallyTo(...)` | Memusatkan elemen horizontal terhadap target |
| `centerVerticallyTo(...)` | Memusatkan elemen vertikal terhadap target |

Semua fungsi tersebut bisa ditambahkan *margin*:

```kotlin
top.linkTo(parent.top, margin = 16.dp)
```

Dan juga bisa digabung dengan **bias** jika kamu ingin mengatur posisi antar dua constraint:

```kotlin
horizontalBias = 0.3f
```

> Layout ini akan menempatkan tombol di tengah atas layar, lalu teks berada di bawah tombol secara sejajar.




#  Barrier

`Barrier` digunakan untuk membuat garis virtual berdasarkan batas beberapa komponen, dan dapat digunakan sebagai anchor untuk elemen lain.

### Kegunaan:

- Menyusun elemen secara dinamis saat ukuran elemen tidak pasti.
- Menentukan batas berdasarkan sisi **terluar** dari beberapa elemen.
- Dapat dibuat dari sisi `start`, `end`, `top`, atau `bottom`.

```kotlin
val barrier = createEndBarrier(button1, button2)
```

Contoh implementasi:

```kotlin
@Composable
fun ConstraintWithBarrier() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start, margin = 16.dp)
            }
        ) {
            Text("Button 1")
        }

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(button1.bottom, margin = 16.dp)
                start.linkTo(parent.start, margin = 32.dp)
            }
        ) {
            Text("Button 2")
        }

        val barrier = createEndBarrier(button1, button2)

        Text(
            "Text dengan Barrier",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(barrier, margin = 16.dp)
            }
        )
    }
}
```

> Teks akan diletakkan di sebelah kanan tombol yang paling kanan, meskipun tombol berbeda ukuran.

`Barrier` adalah **garis virtual** di dalam `ConstraintLayout` yang dibentuk berdasarkan **batas (sisi)** dari satu atau lebih elemen. Barrier tidak terlihat di UI, tetapi bisa digunakan sebagai *anchor* atau titik referensi untuk mengatur posisi elemen lainnya.

---

###  Mengapa Perlu Barrier?

Dalam beberapa kasus, kita ingin meletakkan elemen **setelah** beberapa elemen lain, **tanpa mengetahui ukuran pastinya**. Misalnya:

- Dua tombol memiliki panjang berbeda (karena teks yang berbeda)
- Kita ingin meletakkan teks di sebelah kanan tombol yang paling panjang

Kalau kita hanya menggunakan `linkTo()`, kita harus tahu elemen mana yang paling panjang. Tapi dengan `Barrier`, kita bisa mengatur posisi berdasarkan **sisi terjauh** dari sekelompok elemen—secara **dinamis**.

---

###  Cara Membuat Barrier

Gunakan fungsi `createStartBarrier()`, `createEndBarrier()`, `createTopBarrier()`, atau `createBottomBarrier()`.

Contoh:

```kotlin
val barrier = createEndBarrier(button1, button2)
```

Artinya: buat barrier pada **ujung kanan (end)** dari elemen `button1` dan `button2`. Jika salah satu tombol lebih panjang, barrier akan mengikuti tombol tersebut.

---

###  Cara Menggunakannya

Setelah barrier dibuat, kita bisa menggunakannya sebagai anchor untuk elemen lain:

```kotlin
Text(
    "Text dengan Barrier",
    modifier = Modifier.constrainAs(text) {
        start.linkTo(barrier, margin = 16.dp)
        top.linkTo(parent.top, margin = 32.dp)
    }
)
```

> Teks akan diletakkan di sebelah kanan tombol yang paling panjang (entah `button1` atau `button2`), dengan jarak 16dp.

---

###  Contoh Lengkap

```kotlin
@Composable
fun BarrierExample() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (button1, button2, text) = createRefs()

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(parent.start)
            }
        ) {
            Text("Tombol Pendek")
        }

        Button(
            onClick = {},
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(button1.bottom, margin = 16.dp)
                start.linkTo(parent.start)
            }
        ) {
            Text("Tombol Lebih Panjang")
        }

        val barrier = createEndBarrier(button1, button2)

        Text(
            text = "Diletakkan setelah barrier",
            modifier = Modifier.constrainAs(text) {
                top.linkTo(parent.top, margin = 32.dp)
                start.linkTo(barrier, margin = 16.dp)
            }
        )
    }
}
```

---

###  Sisi-Sisi Barrier

Kamu bisa membuat barrier berdasarkan:

| Fungsi                  | Deskripsi                               |
|-------------------------|-----------------------------------------|
| `createStartBarrier()`  | Berdasarkan sisi kiri (LTR)             |
| `createEndBarrier()`    | Berdasarkan sisi kanan (LTR)            |
| `createTopBarrier()`    | Berdasarkan sisi atas dari elemen       |
| `createBottomBarrier()` | Berdasarkan sisi bawah dari elemen      |


---

##  Chain Layout

`Chain` adalah fitur yang digunakan untuk mengelompokkan beberapa elemen agar diatur secara horizontal atau vertikal dalam satu baris atau kolom dengan pengaturan fleksibel.

### Kegunaan:

- Untuk membuat layout yang rapi, tersusun, dan *auto distribute*.
- Chain bisa dikombinasikan dengan `bias` dan `weight` (melalui `Modifier.weight` jika memakai layout biasa).
- Memiliki beberapa jenis gaya distribusi (`ChainStyle`).

> Chain sangat berguna untuk membuat tampilan **konsisten dan responsif**, bahkan saat jumlah atau ukuran elemen berbeda.

---

###  Cara Membuat Chain

Gunakan fungsi `createHorizontalChain()` atau `createVerticalChain()` dan masukkan referensi elemen yang ingin digabungkan ke dalam chain.

Contoh (horizontal):

```kotlin
val (box1, box2, box3) = createRefs()
createHorizontalChain(box1, box2, box3, chainStyle = ChainStyle.Spread)
```

> Chain ini akan menyusun `box1`, `box2`, dan `box3` secara horizontal dan tersebar merata.

Vertikal pun sama:

```kotlin
createVerticalChain(text1, text2, chainStyle = ChainStyle.Packed)
```

---

###  `ChainStyle` – Gaya Distribusi

`ConstraintLayout` menyediakan beberapa gaya distribusi elemen di dalam chain:

| Gaya             | Deskripsi |
|------------------|-----------|
| `Spread`         | Elemen **tersebar merata**, termasuk ke ujung parent |
| `SpreadInside`   | Elemen **tersebar di dalam**, tapi tidak menyentuh tepi parent |
| `Packed`         | Elemen **dikumpulkan di tengah**, bisa diatur posisinya dengan `bias` |

#### Contoh Visual:

```
Spread:       |A----B----C|

SpreadInside: |--A--B--C--|

Packed:       |   ABC     |
```

---
###  Contoh Implementasi

```kotlin
@Composable
fun HorizontalChainExample() {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (box1, box2, box3) = createRefs()

        createHorizontalChain(box1, box2, box3, chainStyle = ChainStyle.Spread)

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Red)
                .constrainAs(box1) {
                    top.linkTo(parent.top, margin = 32.dp)
                }
        )

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Green)
                .constrainAs(box2) {
                    top.linkTo(parent.top, margin = 32.dp)
                }
        )

        Box(
            modifier = Modifier
                .size(60.dp)
                .background(Color.Blue)
                .constrainAs(box3) {
                    top.linkTo(parent.top, margin = 32.dp)
                }
        )
    }
}
```
> Dengan `ChainStyle.Spread`, ketiga box akan tersebar merata di sepanjang lebar layar.



---
## 🔗 Referensi

- [ConstraintLayout - Jetpack Compose (Android Developers)](https://developer.android.com/develop/ui/compose/layouts/constraintlayout?hl=id)
- [Kode sumber Compose ConstraintLayout](https://github.com/androidx/compose-samples)
