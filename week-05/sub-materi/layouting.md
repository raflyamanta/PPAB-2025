
#  Layouting dengan Jetpack Compose: ConstraintLayout

Jetpack Compose menyediakan `ConstraintLayout` untuk membuat layout yang fleksibel dan kompleks dengan pendekatan deklaratif.

---

##  Menambahkan Dependency

Tambahkan dependency berikut pada file `build.gradle(:app)`:

```kotlin
dependencies {
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.1")
}
```

---

##  Dasar ConstraintLayout

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

> Layout ini akan menempatkan tombol di tengah atas layar, lalu teks berada di bawah tombol secara sejajar.

---

##  Barrier

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

---

##  Chain Layout

`Chain` adalah fitur yang digunakan untuk mengelompokkan beberapa elemen agar diatur secara horizontal atau vertikal dalam satu baris atau kolom dengan pengaturan fleksibel.

### Kegunaan:

- Untuk membuat layout yang rapi, tersusun, dan *auto distribute*.
- Chain bisa dikombinasikan dengan `bias` dan `weight` (melalui `Modifier.weight` jika memakai layout biasa).
- Memiliki beberapa jenis gaya distribusi (`ChainStyle`).

### Jenis ChainStyle:

- `Spread`: elemen tersebar merata
- `SpreadInside`: elemen tersebar tapi tidak menyentuh tepi parent
- `Packed`: elemen dikumpulkan di tengah, bisa disesuaikan dengan bias

Contoh implementasi:

```kotlin
@Composable
fun HorizontalChainLayout() {
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
```
