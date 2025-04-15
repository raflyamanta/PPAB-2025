# Styling with Jetpack Compose

Pada sub-materi ini akan membahas lebih lanjut tentang styling menggunakan jetpack compose. Sub-materi ini akan dibagi lagi menjadi beberapa bagian, diataranya

- Custom Component
- Custom Color
- Custom Font
- Custom Theme

### Custom Component

Komponen adalah bagian kecil dari tampilan (UI) yang bisa berdiri sendiri dan bisa digunakan berulang kali. Menggunakan jetpack compose memberikan kemudahan bagi penggunanya untuk memanfaatkan komponen yang tersedia. Namun, jetpack compose juga memungkinkan kita untuk melakukan kustomisasi component sesuai kebutuhan pengguna.

Pada materi ini kita akan membuat sebuah komponen yang akan diberi nama MyCart referensi seperti gambar berikut.
![MyCart](/week-05/sub-materi/images/cart.jpg)

Pada contoh UI di atas terdapat bagian yang menampilkan nama barang beserta detailnya. Selanjutnya, kita akan mencoba membuat komponen tersebut agar dapat digunakan kembali untuk kondisi yang berbeda.

Buat sebuah fungsi dengan anotasi composable untuk membuat sebuah komponen. Bungkus komponen menggunakan box. Kemudian terdapat screenSize yang berfungsi untuk mendapatkan ukuran layar.

```
@Composable
public fun MyCart(modifier: Modifier = Modifier) {
  val screenSize = LocalConfiguration.current.screenWidthDp

  Box(modifier) {
    // kode di dalam fungsi
  }
}
```

Di dalam box terdapat Row yang digunakan untuk mengatur layout yang disusun ke samping.

```
        Row(
            modifier = modifier
                .border(width = 1.dp, color = Color.Gray)
                .width(screenSize.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
          // kode di dalam fungsi
        }
```

Selain itu, di dalam komponen terdapat fungsi yang digunakan untuk menampilkan gambar. Perlu diingat bahwa file gambar disimpan di dalam folder drawable seperti gambar berikut.

![drawable](/week-05/sub-materi/images/drawable.png)

```
              Image(
                painter = painterResource(id = R.drawable.sepatu),
                contentDescription = "Sepatu",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16))

            )
```

Penulisan kode dilanjutkan sesuai komponen pada referensi yang tertera di atas. Kode akhir dari fungsi MyCart seperti berikut.

```
@Composable
public fun MyCart(modifier: Modifier = Modifier) {
    val screenSize = LocalConfiguration.current.screenWidthDp

    Box(modifier) {
        Row(
            modifier = modifier
                .border(width = 1.dp, color = Color.Gray)
                .width(screenSize.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sepatu),
                contentDescription = "Sepatu",
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16))

            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(

                    ) {
                        Text(text = "Sepatu")
                        Text(text = "Ukuran 36")
                    }
                    IconButton(onClick = { Unit }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Delete"
                        )
                    }
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Rp 150000",
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_24),
                                contentDescription = "Delete"
                            )
                        }
                        Text(text = "1")
                        IconButton(onClick = {}, modifier = Modifier.size(32.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "Add"

                            )
                        }
                    }
                }
            }
        }
    }
}
```

Berikut adalah tampilan ketika aplikasi dijalankan.

![MyCart](/week-05/sub-materi/images/step-1.jpg)

Tentu, tampilan komponen di atas masih kurang enak dilihat dan tidak memiliki kemiripan dengan referensinya, bukan? Simak materi selanjutnya.

### Custom Color

Lihat kembali referensi yang ada di bagian awal. Komponen di atas memiliki warna putih dan terdapat warna hijau di bagian tambah elemen. Lalu bagaimana caranya?

Untuk menambahkan warna, dapat dilakukan dengan menggunakan modifier sebagai berikut

```
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                                .clickable { }
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        )
```

Jika ingin menggunakan warna yang tidak disediakan oleh compose tentunya juga bisa. Caranya dengan memasukkan kode hexadecimal pada kode kita.

```
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.LightGray.copy(alpha = 0.3f))
                                .clickable { }
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF19C563),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        )
```

Bisa juga ditambahkan ke dalam colors.xml agar font mudah digunakan kembali.

```
<color name="ijo_custom">#19C563</color>
```

contoh penggunaannya seperti berikut

```
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "Add",
                                modifier = Modifier.size(16.dp),
                                tint = colorResource(id = R.color.ijo_custom)
                            )
```

Ketika aplikasi dijalankan akan tampak seperti gambar berikut.

![step-2](/week-05/sub-materi/images/step-2.jpg)

### Custom Font

Untuk mengatur ukuran, tebal, font family, dapat memanfaat atribut yang ada di dalam method text seperti berikut ini.

```
                        Text(
                            text = "Sepatu Lucu Sekali",
                            fontSize = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                        )
                        Text(
                            text = "Ukuran 36",
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
```

Setelah semua text disesuaikan ukurannya, aplikasi akan tampak seperti gambar di bawah.

![step-3a](/week-05/sub-materi/images/step-3a.jpg)

Jika ingin mengubah font dan mengambil font dari Google Fonts, tambahkan dependencies di dalam files build.gradle.kts bagian module app, kemudian jangan lupa di sinkronkan.

```
dependencies {

    ...
    implementation("androidx.compose.ui:ui-text-google-fonts:1.7.8")
}
```

kemudian download font yang diinginkan, untuk percobaan ini download font montserrat dari google fonts.
![google-fonts](/week-05/sub-materi/images/font.png)

setelah font selesai di export, masukkan ke dalam folder font.
![fonts](/week-05/sub-materi/images/font-2.png)
jika tidak terdapat folder font, buat dengan `res > New > Android Resource Directory` pilih directory type font.

Setelah itu, font dapat digunakan.

```
                        Text(
                            text = "Sepatu Lucu Sekali",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                        )
```

Hasil setelah menerapkan font baru.
![step-3b](/week-05/sub-materi/images/step-3b.jpg)

Praktikan dapat menerapkan metode lain seperti pada sumber lainnya untuk dijelajahi secara mandiri.
[Android Fonts](https://developer.android.com/develop/ui/compose/text/fonts#kotlin)

### Custom Theme

Kita juga dapat mengatur tema sesuai keinginan dan kebutuhan. Untuk membuat tema sendiri (misal greenTheme), buat file baru di dalam folder ui.
![theme](/week-05/sub-materi/images/theme.png)

Lalu atur tema di dalam file GreenTheme seperti contoh sederhana berikut.

```
private val GreenColorScheme = lightColorScheme(
    primary = Color(0xFF19C563),
    onPrimary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
)

@Composable
fun GreenTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = GreenColorScheme,
        typography = Typography,
        content = content
    )
}
```

Penggunaannya cukup mudah dengan memanggilnya di dalam UI yang ingin menerapkan tema tertentu.

```
 override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenTheme {
               // lanjutan kode
            }
        }
    }
```

Ketika aplikasi dijalankan, tampilan yang muncul seperti gambar berikut.

![step-4](/week-05/sub-materi/images/step-4.jpg)

Praktikan juga dapat menggunakan bantuan untuk memudahkan dalam membuatan tools. Berikut terdapat sebuah tool yang berguna untuk membuat tema secara otomatis.
[Theme Builder](https://material-foundation.github.io/material-theme-builder/)

### Kode Akhir

```
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenTheme {
                Scaffold(modifier = Modifier.fillMaxSize())
                { innerPadding ->
                    MyCart(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
public fun MyCart(modifier: Modifier = Modifier) {
    val screenSize = LocalConfiguration.current.screenWidthDp

    Box(
        modifier
    ) {
        Row(
            modifier = modifier
                .width(screenSize.dp)
                .background(Color.White, RoundedCornerShape(4))
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sepatu),
                contentDescription = "Sepatu",
                modifier = Modifier
                    .size(112.dp)
                    .clip(RoundedCornerShape(16)),

                )
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy((-16).dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(

                    ) {
                        Text(
                            text = "Sepatu Lucu Sekali",
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                        )
                        Text(
                            text = "Ukuran 36",
                            fontFamily = FontFamily.Default,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                    IconButton(onClick = { Unit }, modifier = Modifier.size(20.dp)) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Delete",
                        )
                    }
                }

                Row(
                    modifier = modifier
                        .fillMaxWidth(),

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = "Rp 150000",
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { }
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_remove_24),
                                contentDescription = "Delete",
                                modifier = Modifier.size(16.dp), tint = Color.DarkGray
                            )
                        }

                        Text(
                            text = "1", color = Color.DarkGray, fontWeight = FontWeight.SemiBold
                        )

                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { }
                                .border(
                                    width = 1.dp,
                                    color = Color(0xFF19C563),
                                    shape = RoundedCornerShape(12.dp)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_add_24),
                                contentDescription = "Add",
                                modifier = Modifier.size(16.dp),
                                tint = colorResource(id = R.color.ijo_custom)
                            )
                        }
                    }
                }
            }
        }
    }
}
```

### Referensi

https://developer.android.com/develop/ui/compose/designsystems/custom
https://developer.android.com/develop/ui/compose/text/fonts#kotlin
https://material-foundation.github.io/material-theme-builder/
