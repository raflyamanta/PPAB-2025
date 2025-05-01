# Implementasi Lazy Layout

Sekarang, kita akan mencoba membuat tampilan menggunakan lazy layout. Masih ingatkah materi minggu lalu membuat komponen menggunakan jetpack compose yang menampilkan tampilan seperti di bawah ini?

![step-4](/week-05/sub-materi/images/step-4.jpg)

Sekarang kita akan melanjutkannya dengan menyimpan daftar barang di sebuah list, kamudian menampilkannnya pada layar menggunakan lazy layout.

Agar struktur file menjadi lebih rapi dan mudah untuk dibaca, pada percobaan ini akan mengatur struktur seperti berikut.

```
com.l0122137.ppab_05/
├── MainActivity.kt
├── data/
│   └── model/
│       └── CartItem.kt
└── ui/
    ├── screen/
    │   └── CartScreen.kt
    ├── component/
    │   ├── CartItemRow.kt
    │   ├── Navbar.kt
    │   └── OrderSummary.kt
    └── theme/

```

Struktur file di atas memiliki fungsinya masing-masing. Mari kita bedah untuk membuat lazy layout.

##### `CartItem.kt`

`data class CartItem()` merupakan kelas data yang digunakan untuk merepresentasikan item dalam belanja termasuk properti apa saja yang ada di dalamnya.

`val cartItems = listOf()` merupakan list item yang ada di dalam cartItem. List ini merepresentasikan barang apa saja yang ada di dalam keranjang untuk kemudian akan ditampilkan dalam lazy list

```
  data class CartItem(
      val title: String,
      val size: String,
      val price: Int,
      val quantity: Int,
      val imageResId: Int
  )

  val cartItems = listOf(
      CartItem("Sepatu Lucu Sekali", "Ukuran 36", 150000, 1, R.drawable.sepatu),
      CartItem("Sepatu Lari", "Ukuran 42", 200000, 2, R.drawable.sepatu),
      CartItem("Sepatu Anak", "Ukuran 30", 120000, 1, R.drawable.sepatu),
      CartItem("Sepatu Gunung", "Ukuran 44", 250000, 1, R.drawable.sepatu),
      CartItem("Sandal Santai", "Ukuran 38", 75000, 3, R.drawable.sepatu),
      CartItem("Sneakers Putih", "Ukuran 40", 180000, 2, R.drawable.sepatu),
      CartItem("Sepatu Kantor", "Ukuran 41", 220000, 1, R.drawable.sepatu),
      CartItem("Boots Kulit", "Ukuran 43", 300000, 1, R.drawable.sepatu),
      CartItem("Slip On Hitam", "Ukuran 39", 110000, 2, R.drawable.sepatu),
      CartItem("Sepatu Casual", "Ukuran 37", 130000, 1, R.drawable.sepatu),
      CartItem("Running Shoes Pro", "Ukuran 45", 280000, 1, R.drawable.sepatu),
      CartItem("Sandal Jepit", "Ukuran 35", 90000, 4, R.drawable.sepatu),
  )

```

##### `CartItemRow.kt`

Masih ingatkah composable yang kita buat di pertemuan sebelumnya? Nah, untuk membuat struktur file lebih rapi, komponen tersebut di file terpisah bernama `CartItemRow.kt'. Ubah beberapa detail yang ingin diubah jika perlu.

```
@Composable
fun CartItemRow(cartItem: CartItem, modifier: Modifier = Modifier) {
    val screenSize = LocalConfiguration.current.screenWidthDp

    Box(modifier) {
        Row(
            modifier = modifier
                .width(screenSize.dp)
                .background(Color.White, RoundedCornerShape(4))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = cartItem.imageResId),
                contentDescription = cartItem.title,
                modifier = Modifier
                    .size(112.dp)
                    .clip(RoundedCornerShape(16))
            )
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy((16).dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column {
                        Text(
                            text = cartItem.title,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            fontWeight = FontWeight.SemiBold,
                            color = Color.DarkGray,
                        )
                        Text(
                            text = cartItem.size,
                            fontSize = 16.sp,
                            color = Color.DarkGray
                        )
                    }
                    IconButton(
                        onClick = { /* TODO: Remove */ },
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Delete"
                        )
                    }
                }

                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Rp ${cartItem.price}",
                        fontSize = 20.sp,
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
                                contentDescription = "Remove",
                                modifier = Modifier.size(16.dp),
                                tint = Color.DarkGray
                            )
                        }

                        Text(
                            text = cartItem.quantity.toString(),
                            color = Color.DarkGray,
                            fontWeight = FontWeight.SemiBold
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

##### `CartScreen.kt`

Inilah saatnya kita menerapkan lazy layout. Kode di dalam CartScreen menggunakan LazyColumn sederhana untuk menampilkan daftar barang yang tersimpan di dalam list `cartItems.kt`. Komponen dalam `CartItemRow.kt` digunakan untuk memberikan tampilan untuk setiap itemnya.

```
@Composable
fun MyCart(modifier: Modifier = Modifier, cartItems: List<CartItem>) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(cartItems) { item ->
            CartItemRow(cartItem = item)
        }
    }
}
```

##### `MainActivity`

Tidak selesai sampai disitu, kita masih perlu memanggil komponen agar dapat ditampilkan ketika aplikasi dibuka.

Untuk mempercantik tampilan agar terlihat seperti halaman keranjang di aplikasi pada umumnya, mari kita buat sebuah komponen yang disimpan di dalam file `OrderSummary.kt`

Di dalam file `OrderSummary.kt` terdapat 2 fungsi yaitu `OrderSummarySection()` dan `SummaryRow()`. Sederhananya, `SummaryRow()` dipanggil di dalam `OrderSummarySection()` untuk membuat satu baris ringkasan seperti Subtotal, Ongkir, dan Total agar kode komponen terlihat lebih rapi.

Di dalam fungsi `OrderSummarySection()` juga terdapat logika untuk pelakukan perhitungan. Subtotal merupakan jumlah harga berdasarkan barang-barang yang tersimpan di dalam cartItems. Ongkir diasumsikan bahwa harga ongkos kirim merupakan 10% dari jumlah harga barang. dan Total merupakan jumlah keseluruhan subtotal dan ongkir.

```
@Composable
fun OrderSummarySection() {

    val subtotal = cartItems.sumOf { it.price * it.quantity }
    val ongkir = (subtotal * 0.1).toInt()
    val total = subtotal + ongkir


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, spotColor = Color.LightGray)
            .background(Color.White, shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .padding(16.dp, 4.dp)
    ) {
        SummaryRow("Subtotal", subtotal)
        SummaryRow("Ongkir", ongkir)
        SummaryRow("Total", total, isBold = true)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Aksi saat Checkout */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        ) {
            Text(text = "Checkout")
        }
    }
}

@Composable
fun SummaryRow(label: String, amount: Int, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = "Rp $amount",
            fontSize = 16.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

```

Selain itu, kita juga perlu untuk membuat navbar sederhana di bagian atas layar yang disimpan di dalam file `Navbar.kt`

```
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navbar() {
    TopAppBar(
        modifier = Modifier
            .height(84.dp)
            .shadow(8.dp, spotColor = Color.LightGray),

        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),

                ) {
                Text(
                    text = "Cart",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.White,
        )
    )
}
```

Semuanya sudah selesai, sekarang gilirannya untuk memanggil komponen-komponen yang telah dibuat ke dalam `MainActivity`. Berikut kode lengkap dari kelas `MainActivity`.

```
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GreenTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    // Menambahkan komponen Navbar
                    topBar = { Navbar() },
                    // Menambahkan komponen OrderSummarySection di bagian bawah
                    bottomBar = {
                        OrderSummarySection()
                    }) { innerPadding ->
                   // Memanggil komponen MyCart yang telah dibuat sebelumnya
                    MyCart(modifier = Modifier.padding(innerPadding), cartItems = cartItems)

                }
            }
        }
    }
}

```

Berikut adalah hasil akhir ketika aplikasi dijalankan
![result](/week-06/images/result.gif)
