# Collections

_Collections_ digunakan untuk menyimpan dan memanipulasi kelompok objek atau data. Terdapat dua jenis _collections_, yaitu `mutable` dan `immutable`. _Collections_ yang bersifat **_mutable_ dapat diubah** nilainya, sedangkan _collections_ yang bersifat **_immutable_** tidak dapat diubah nilainya atau bersifat **_read-only_**. Terdapat berbagai macam collections pada Kotlin, yaitu : 
- List
- Set
- Map

## List

List adalah kumpulan elemen terurut yang dapat berisi duplikat. Elemen-elemennya dapat diakses menggunakan indeks.

### Mutable List

```kotlin
fun main() {
    val mutableList = mutableListOf(1, 2, 3, 4, 5)
    println("Mutable List: $mutableList")

    mutableList.add(6)
    mutableList.removeAt(1)
    mutableList[2] = 10

    println("Modified Mutable List: $mutableList")
}

// Output
// Mutable List: [1, 2, 3, 4, 5]
// Modified Mutable List: [1, 3, 10, 5, 6]
```

### Immutable List

```kotlin
fun main() {
    // Immutable List
    val immutableList = listOf(1, 2, 3, 4, 5)
    println("Immutable List: $immutableList")
}

// Output
// Immutable List: [1, 2, 3, 4, 5]
```

## Set

Set adalah kumpulan elemen yang tidak terurut dan tidak boleh memiliki duplikat. 

### Mutable Set

```kotlin
fun main() {
    // Mutable Set
    val mutableSet = mutableSetOf(1, 2, 3, 4, 5)
    println("Mutable Set: $mutableSet")

    mutableSet.add(6)
    mutableSet.remove(3)

    println("Modified Mutable Set: $mutableSet")
}

// Output
// Mutable Set: [1, 2, 3, 4, 5]
// Modified Mutable Set: [1, 2, 4, 5, 6]
```

### Immutable Set

```kotlin
fun main() {
    // Immutable Set
    val immutableSet = setOf(1, 2, 3, 4, 5)
    println("Immutable Set: $immutableSet")
}

// Output
// Immutable Set: [1, 2, 3, 4, 5]
```

## Map

Map adalah kumpulan pasangan kunci-nilai di mana setiap kunci unik dan memiliki nilai yang terkait.

### Mutable Map

```kotlin
fun main() {
    // Mutable Map
    val mutableMap = mutableMapOf("one" to 1, "two" to 2, "three" to 3)
    println("Mutable Map: $mutableMap")

    mutableMap["four"] = 4
    mutableMap.remove("two")
    mutableMap["three"] = 30

    println("Modified Mutable Map: $mutableMap")
}

// Output
// Mutable Map: {one=1, two=2, three=3}
// Modified Mutable Map: {one=1, three=30, four=4}
```

### Immutable Map

```kotlin
fun main() {
    // Immutable Map
    val immutableMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println("Immutable Map: $immutableMap")
}

// Output
// Immutable Map: {one=1, two=2, three=3}
```

## Kotlin Collections
Kotlin Collections juga memiliki berbagai fungsi operasi yang dapat digunakan untuk mengakses data didalamnya, diantaranya : 

| Operasi | Fungsi |
|---------|---------|
| filter() | Mengembalikan koleksi yang berisi elemen-elemen yang memenuhi kondisi tertentu.  | 
| filterNot() | Mengembalikan koleksi yang berisi elemen-elemen yang tidak memenuhi kondisi tertentu.  |
| map() | Mengubah setiap elemen dalam koleksi menjadi elemen baru sesuai dengan transformasi yang diberikan.  | 
| count() | Menghitung jumlah elemen dalam koleksi yang memenuhi kondisi tertentu.  |
| find() | Mengembalikan elemen pertama dalam koleksi yang memenuhi kondisi tertentu, atau _null_ jika tidak ada yang cocok.  | 
| firstOrNull() | Mengembalikan elemen pertama dalam koleksi, atau null jika koleksi kosong.  |
| lastOrNull() | Mengembalikan elemen terakhir dalam koleksi, atau null jika koleksi kosong.  | 
| first() | Mengembalikan elemen pertama dalam koleksi.  |
| last() | Mengembalikan elemen terakhir dalam koleksi.  | 
| sum() | Menghitung total nilai dari elemen-elemen dalam koleksi.  |
| sorted() | Mengurutkan elemen-elemen dalam koleksi.  | 
| fold() | Menggabungkan elemen-elemen koleksi dengan nilai awal tertentu menggunakan operasi yang ditentukan.  |
| foldRight() | Mirip dengan `fold()`, namun prosesnya dimulai dari elemen terakhir hingga elemen pertama.  | 
| drop() | Menghapus sejumlah elemen pertama dari koleksi dan mengembalikan sisanya.  |
| dropLast() | Menghapus sejumlah elemen terakhir dari koleksi dan mengembalikan sisanya.  | 
| slice() | Mengambil sebagian elemen dari koleksi berdasarkan indeks yang ditentukan.  |
| distinct() | Menghapus duplikat dari koleksi dan mengembalikan elemen-elemen unik.  | 
| distinctBy() | Menghapus duplikat berdasarkan kriteria tertentu dari koleksi dan mengembalikan elemen-elemen unik.  |
| chunked() | Membagi koleksi menjadi bagian-bagian dengan ukuran tertentu.  | 


**Contoh kode** dapat dilihat pada file `operations.kt`.

<br/>