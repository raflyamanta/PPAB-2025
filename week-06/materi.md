# Android UI Components 3

Pada pertemuan minggu ini kita masih melanjutkan materi sebelumnya tentnag Android UI Component. Kita akan belajar tentang Lazy Columns.

Lazy Columns merupakan salah satu fungsi dalam Jetpack Compose yang sangat berguna untuk menampilkan sekumpulan item. Fungsi ini akan berguna jika ingin menampilkan konten yang dapat di **scroll** baik secara horizontal maupun secara vertikal.

Terdapat kemiripan antara Lazy List khususnya `LazyColumn` dengan `Column` layout yang pernah dipelajari di minggu sebelumnya. Penggunaan `Column` pada device akan di-compose (di-render) sekaligus pada layar walaupun komponen sudah tidak terlihat di layar. Sedangkan `LazyColumn` hanya akan me-compose komponen yang hanya terlihat pada layar sehingga komponen ini cocok digunakan untuk jumlah elemen yang banyak.

Outline yang akan kita pelajari pada minggu ingi diantaranya adalah

- [Lazy List](#lazy-list)
- [LazyListScope DSL](#lazylistscope-dsl)
- [Lazy Grids](#lazy-grids)
- [Lazy Staggered Grid](#lazy-staggered-grid)

### Lazy List

Lazy List sangat berguna ketika ingin menampilkan items dengan jumlah besar. Compose menyediakan komponen yang diberi nama `LazyColumn` dan `LazyRow`. Sesuai namanya, `LazyColumn` untuk menampilkan daftar _scroll_ secara vertikal, sedangkan LazyRow untuk menampilkan daftar _scroll_ secara horizontal.

```
  LazyColumn{
    ....
  }

  LazyRow{
    ....
  }

```

### LazyListScope DSL

LazyListScope DSL menyediakan fungsi untuk mendefinisikan items di dalam layout. Beberapa diantarannya adalah `item()` untuk menambahkan item satu per satu maupun `items(Int)` untuk mneambahkan banyak item sekaligus. Penggunaan `items()` juga dapat digunakan untuk collection of items seperti List.

```
LazyColumn {
    // Add a single item
    item {
        Text(text = "First item")
    }

    // Add 5 items
    items(5) { index ->
        Text(text = "Item: $index")
    }

    // Add another single item
    item {
        Text(text = "Last item")
    }
}

// untuk mneambahkan items dari collection of items seperti List
LazyColumn {
    items(messages) { message ->
        MessageRow(message)
    }
}

```

### Lazy Grids

`LazyVerticalGrid` dan `LazyHorizontalGrid` merupakan komponen yang digunakan untuk menampilkan items ke dalam sebuah grid. `LazyVerticalGrid` akan menampilkan kontainer grid yang dapat di scroll secara vertikal, sedangkan `LazyHorizontalGrid` membuat kontainer dapat discroll secara horizontal.

```
  LazyVerticalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp)
  ) {
      items(photos) { photo ->
          PhotoItem(photo)
      }
  }

    LazyHorizontalGrid(
      columns = GridCells.Adaptive(minSize = 128.dp)
  ) {
      items(photos) { photo ->
          PhotoItem(photo)
      }
  }
```

### Lazy Staggered Grid

`LazyVerticalStaggeredGrid` dan `LazyHorizontalStaggeredGrid` merupakan komponen yang memungkinkan grid akan dimuat secara lambat dan bertingkat.

```
  LazyVerticalStaggeredGrid(
      columns = StaggeredGridCells.Adaptive(200.dp),
      verticalItemSpacing = 4.dp,
      horizontalArrangement = Arrangement.spacedBy(4.dp),
      content = {
          items(randomSizedPhotos) { photo ->
              AsyncImage(
                  model = photo,
                  contentScale = ContentScale.Crop,
                  contentDescription = null,
                  modifier = Modifier
                      .fillMaxWidth()
                      .wrapContentHeight()
              )
          }
      },
      modifier = Modifier.fillMaxSize()
  )
```

---

Untuk melihat bagaimana Implementasi Lazy Layout dalam aplikasi Android, dapat dilihat melalui halaman [Latihan](/week-06/praktik.md)

Lebih lengkapnya dapat dikulik sendiri untuk meningkatkan pengetahuan tentang Lazy Layout melalui https://developer.android.com/develop/ui/compose/lists#lazy-staggered-grid
