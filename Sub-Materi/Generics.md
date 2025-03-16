# Kotlin Generics

Generics merupakan suatu konsep yang memungkinkan kelas atau interface menjadi tipe parameter yang dapat digunakan untuk berbagai tipe data.

## Kelas Generic

Penenrapan generic dapat dilakukan dengan **meletakkan tipe parameter dalam _angle brackets_ (<>)** seperti contoh berikut :

```kotlin
interface List<T>{
    operator fun get(idx: Int): T
}
```

Tipe parameter T digunakan dalam kode di atas sebagai tipe reguler yang mengembalikan tipe fungsi. Kita juga dapat menggambarkannya sebagai _placeholder_ yang dapat kita bebaskan untuk menentukan tipe dasar yang didukung oleh kode. 

Sebuah kelas yang mewarisi kelas atau _interface_ generic memerlukan penentuan tipe argumen sebagai tipe dasar dari parameter generic untuk kelas tersebut. Berikut contoh kodenya :

```kotlin
class LongList : List<Long>{
    override fun get(idx: Int): Long {
        return this[idx]
    }
}
```

Selain dapat mewarisi kelas atau _interface_ generic, kita juga dapat membuat sebuah kelas generic yang mewarisi kelas atau _interface_ generic. Hal ini dapat dilakukan dengan menambahkan kode generic seperti kode di bawah ini : 

```kotlin
class ArrayList : List<P>{
    override fun get(idx: Int): P {
        return this[idx]
    }
}
```

Dengan kode tersebut, ketika mengggunakan kelas `ArrayList`, kita perlu menentukan tipe argumen yang akan digunakan. Hal tersebut dapat dilakukan dengan : 

```kotlin
fun main{
    val arrayList = ArrayList<Double>()
    val firstDouble = arrayList.get(0)
}
```

## Fungsi Generic

Jika kita membuat sebuah fungsi yang berhubungan dnegan List, maka fungsi generic akan dibutuhkan. Misalnya, kita ingin membuat list yang dapat digunakan untuk berbagai tipe dan tidak terpaku pada tipe tertentu.

Tipe parameter yang dimiliki fungsi generic ditentukan ketika fungsi dipanggil. Metode pendeklarasiannya sedikit berbeda dengan kelas generic. Tipe parameter di dalam angle bracket harus ditempatkan sebelum nama fungsi yang ditentukan.

```kotlin
fun <T> run(): T {
    /*...*/
}
```

Berikut adalah contoh penerapan generic pada fungsi : 

```kotlin
fun <T> List<T>.getElementAt(indices: Iterable<Int>): List<T> {
    val result = mutableListOf<T>() // Membuat list untung menyimpan hasil
    // Melakukan iterasi terhadap semua index dalam List
    for (index in indices) {
        // Mengecek apakah index berada dalam range index List
        if (index in 0 until size) { 
            result.add(this[index]) // Memasukkan nilai pada index yang diminta
        }
    }
    return result
}

fun main() {
    val numbers = listOf(10, 20, 30, 40, 50)

    // Mengambil elemen di indeks 1 dan 3
    val result = numbers.getElementAt(listOf(1, 3))

    println("Selected elements: $result") 
}

// Output
// Selected elements: [20, 40]
```

## Constraint Type Parameter

Pembatasan berbagai tipe yang dapat digunakan sebagai parameter dapat dilakukan pada penerapan generics. Penentuan batas tersebut dapat dilakukan dengan menambahkan titik dua (`:`) setelah tipe parameter, kemudian diikuti oleh tipe yang akan dijadikan batasan. Contoh kode seperti berikut :

```kotlin
class class NumberList<T : Number> : MyList<T>{
    override fun get(idx: Int): T {
        return this[idx]
    }
}
```

Pada kode di atas, batasan tipe argumen untuk kelas `NumberList` adalah **Number**. Compiler akan menunjukkan _error_ jika tipe argumen pada _class_ tersebut selain Int, Long, Double, dan tipe angka lainnya.

```kotlin
fun main() {
    val num1 = NumberList<Int>()
    val num2 = NumberList<Double>()// error : Type argument is not within its bounds
}
```

Constraint Type Parameter juga dapat digunakan pada Fungsi Generic seperti pada contoh berikut : 

```kotlin
fun <T : Number> MyList<T>.sumNumber() : T {
    /* .. */
}
```

Untuk penjelasan lebih lanjut mengenai **Constraint Type Parameters** pada Kotlin, kunjungi link [berikut](https://kotlinlang.org/spec/kotlin-type-constraints.html).

<br/>