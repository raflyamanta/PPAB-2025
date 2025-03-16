# Kotlin Functional Programming

Dalam penulisan sintaksnya, Kotlin dapat menggunakan gaya _functional programming_. Functional Programming mengacu pada penggunaan fungsi sebagai unit utama komputasi. Paradigma ini menganggap fungsi sebagai objek pertama yang dapat disimpan dalam variabel, dikirim sebagai argumen ke fungsi lain, atau bahkan dihasilkan sebagai output dari fungsi lain. 

Terdapat beberapa sub-materi yang akan dibahas pada _functional programming_, yaitu : 
- Lambda
- Kotlin Standard Library
- Member Reference

###= Lambda
Lambda expression atau _anonymous function_ adalah sebuah fungsi yang tidak memiliki nama. Namun, seperti fungsi pada umumnya, lambda expression juga dapat memiliki **parameter**, **body**, dan **return type**. 

### Regular Function

```kotlin
fun morning(name: String): String{
    return "Good Morning $name!"
}
```

### Function with Lambda

```kotlin
val morning : (String) -> String = { name: String ->
    "Good Morning $name!"
}
```

### Karakteristik Lambda

- Dengan lambda, kita tidak perlu menentukan tipe tertentu untuk nilai kembaliannya. Kompiler secara otomatis akan menentukan jenisnya.
- Karena lambda bersifat anonymous, saat dideklarasikan, lambda tidak membutuhkan kata kunci fun dan modifikasi visibilitas karena tetap merupakan fungsi.
- Parameter akan dimasukkan ke dalam kurung kurawal {}. Ketika ingin mengembalikan nilai, kompiler akan secara otomatis mengembalikannya dari dalam body. Dengan demikian, kata kunci return tidak lagi diperlukan.
- Expression lambda dapat disimpan ke dalam variabel atau digunakan sebagai argumen untuk parameter.

### Penggunaan Lambda Expression

```kotlin
val morning = { println("Good Morning!") }
```

Kode di atas merupakan sebuah contoh penggunaan _lambda expression_ secara singkat. Kode tersebut dapat dijalankan dengan cara berikut : 

```kotlin
fun main(){
    morning()
}

// Output
// Good Morning!
```

Jika kita ingin menambahkan parameter pada _lambda expression_, kita dapat melakukannya seperti berikut :

```kotlin
val printMsg = { msg: String -> println(msg) }

fun main(){
    printMsg("Hello Future!")
}

// Output
// Hello Future!
```

Dalam kurung kurawal, terdapat **parameter** lambda, seperti yang disebutkan sebelumnya. Untuk membedakannya dari **body**, tanda `"->"` ditambahkan setelah daftar **parameter** yang ada. Lalu, bagaimana cara mendeklarasikan lambda untuk dapat mengembalikan nilai? Hal tersebut dapat dilakukan dengan cara berikut : 

```kotlin
val add = { a: Int, b: Int -> println(a + b) }

fun main(){
    add(1,2)
}

// Output
// 3
```

### Perulangan dengan Lambda

#### **forEach**
```kotlin
fun main(){
    ranges.forEach{ print("$it ") }
    println()
}

// Output
// 1 3 5 7
```

#### **forEachIndexed**

Merupakan perulangan seperti `forEach`, tetapi dengan mendapatkan indeks dari tiap nilai yang dicakup

```kotlin
fun main(){
    ranges.forEachIndexed { idx, num -> 
        print("$idx - $num  ") 
    }
    println()
}

// Output
// 0 - 1  1 - 3  2 - 5  3 - 7
```

## Kotlin Standard Library

**Standard Function Library** adalah sebuah extension functions dari sebuah objek yang menggunakan lambda sebagai argumen atau yang disebut sebagai _higher-order function_.

### **Scope Function**

Scope function adalah fungsi yang disediakan oleh Kotlin Standard Library yang digunakan untuk mengeksekusi blok kode di dalam konteks sebuah objek. Fungsi-fungsi ini memungkinkan Anda untuk bekerja dengan objek secara lebih ekspresif dan mudah, serta memfasilitasi penggunaan lambda expressions dengan ruang lingkupnya sendiri.

### **Context Object**

Terdapat **dua cara** untuk mengakses konteks sebuah objek dari dalam lambda yang menjadi bagian dari scope function. Cara pertama adalah diakses sebagai **lambda receiver** atau `this`. Sedangkan cara kedua yaitu diakses sebagai **lambda argument** atau `it`. Kapabilitas yang sama dimiliki oleh keduanya, tetapi penggunaannya untuk kasus yang berbeda.  

### **Lambda Receiver** atau `this`

Beberapa fungsi yang menggunakan lambda receiver adalah `run`, `with`, dan `apply`.

#### **run**

Jika di dalam blok lambda terdapat **inisialisasi objek** dan **perhitungan nilai kembalian**, fungsi `run` akan sangat bermanfaat karena fungsi ini akan menggunakan _receiver_ (`this`) untuk mengakses konteks objek dan mengembalikan nilai berdasarkan ekspresi yang ada di dalam blok lambda.

```kotlin
fun main() {
    val sentence = "I love programming"
    val result = sentence.run {
        val original = this
        val length = this.length
        "Length of text $original is $length"
    }
    println("Result: $result")
}

// Output
// Result: Length of text I love programming is 18
```

#### **with**

Fungsi `with` bukanlah merupakan sebuah _extension_, melainkan hanya fungsi biasa. Fungsi `with` menyematkan konteks objek sebagai argumen, kemudian oleh blok lambda diakses sebagai receiver.

```kotlin
fun main() {
    val text = "Hello, Kotlin!"

    val result = with(text) {
        println("Original text: $this")
        val modifiedText = this.replace("Kotlin", "World")
        println("Modified text: $modifiedText")
        "Modified text length: ${modifiedText.length}"
    }

    println(result)
}

// Output
// Original text: Hello, Kotlin!
// Modified text: Hello, World!
// Modified text length: 13
```

Fungsi `with` disarankan untuk mengakses apa yang menjadi anggotanya tanpa harus menyediakan nilai kembalian. Fungsi with juga berguna untuk mengurangi _boilerplate_ pada kode yang kita buat.

#### **apply**

Fungsi `apply` mengembalikan nilai dari konteks objeknya, dimana objek konteksnya merupakan _receiver_ (`this`). Fungsi `apply` sebaiknya dapat melakukan inialisasi atau konfigurasi dari _receiver_-nya.

```kotlin
fun main(){
    val text = StringBuilder().apply{
        append("Hi! ")
        append("Nice to meet you :)")
    }

    println(text.toString())
}

// Output
// Hi! Nice to meet you :)
```

Metode ini memiliki kekurangan: kita tidak dapat membedakan objek penerima dengan objek di luar lingkup fungsi. Cara ini lebih berfokus pada fungsi objek itu sendiri, seperti memanggil fungsi dan menginialisasi nilai anggota yang termasuk dalam objek.

### **Lambda Argument** atau `it`

Terdapat dua fungsi yang menggunakan lambda argument untuk mengakses konteks dari sebuah objek, yaitu fungsi `let` dan `also`. Berbeda dengan sebelumnya, pada fungsi ini, produksi atau inialisasi nilai dari argumen untuk variabel lain dapat dilakukan.

Secara default, nama dari argumen pada fungsi ini adalah `it`, tetapi nama tersebut dapat kita ubah.

#### **let**

Fungsi `let` banyak digunakan dalam objek bertipe **_nullable_**

```kotlin
fun main(){
    val nim: String? = null
    nim?.let { 
        val length = it.length * 2
        val text = "nim length is $length"
        println(text)
    }
}

// Output : (Kosong)
```

Dengan menggunakan fungsi `let`, kita dapat melakukan pengecekan dengan mengurangi penggunaan operator safe call seperti kode berikut : 

```kotlin
fun main(){
    val nim: String? = null
    val length = nim?.length ?: 0 * 2
    val text = "nim length is $length"
    println(text)
}

// Output
// nim length is 0
```

Kita juga dapat menjalankan logika kode lain jika objeknya bernilai **null**. Hal inindapat dilakukan dengan memanfaatkan penggunaan fungsi `run` dan _elvis operator_, sehingga kode menjadi seperti berikut : 

```kotlin
fun main(){
    val nim: String? = null
    nim?.let { 
        val length = it.length * 2
        val text = "nim length is $length"
        println(text)
    } ?: run{
        val text = "nim is null"
        println(text)
    }
}

// Output
// nim is null
```

Nilai kembalian pada fungsi `let` bergantung pada _expression_ yang berada di dalam blok lambda. Karena pada baris terakhir dari blok lambda tersebut adalah fungsi` println()`, maka nilai yang akan dikembalikan adalah **Unit**. Hal ini dikarenakan fungsi `println()` sendiri mengembalikan nilai **Unit**.

#### **also**

Fungsi `also` mirip seperti fungsi `apply`, dimana kembalian nilainya adalah konteks objek. Perbedaannya terletak pada bentuk konteks objeknya yaitu sebagai argumen (`it`). Jika kita ingin menggunakan konteks dari objek sebagai argumen tanpa harus mengubah nilainya, kita sebaiknya menggunakan fungsi `also`.

```kotlin
fun main() {
    val numbers = mutableListOf(1, 2, 3, 4, 5)
    
    val result = numbers.also {
        it.add(6)
        println("List after adding element: $it")
    }

    println("Original list: $numbers")
    println("Result: $result")
}

// Output
// List after adding element: [1, 2, 3, 4, 5, 6]
// Original list: [1, 2, 3, 4, 5, 6]
// Result: [1, 2, 3, 4, 5, 6]
```

Fungsi `also` juga memodifikasi list `numbers`.

Scope function sangat berguna untuk bekerja dengan objek secara ringkas dan ekspresif, sambil menghindari duplikasi kode. Untuk penjelasan lebih lanjut mengenai **scope function**, kunjungi link [berikut](https://kotlinlang.org/docs/scope-functions.html).

## Member Reference

Kotlin memungkinkan kita untuk memisahkan lambda expression menjadi fungsi tersendiri dan mereferensikannya langsung sebagai _instance_ dari function type seperti contoh berikut:

```kotlin
fun add(a: Int, b: Int): Int = a+ b

val sum: (Int, Int) -> Int = ::count
```

Kode tersebut ditulis menggunakan mekanisme **Reflection** yang memungkinkan kita untuk  mengamati struktur kode dari proyek yang sedang kita kerjakan secara langsung.

### Function References

Kita juga dapat mengimplementasikan _member references_ pada sebuah fungsi. Dengan menggunakan operator `::`, kita dapat menggunakannya sebagai _instances_ dari function type.

```kotlin
fun length(name: String): Int {
    return name.length
}

fun main() {
    val names = listOf("John", "Alice", "Bob", "Charlie")

    val sortedNames = names.sortedBy(::length) 

    println(sortedNames)
}

// Output
// [Bob, John, Alice, Charlie]
```

Selain itu, kita juga dapat menggunakannya pada _extension function_ seperti contoh kode berikut :

```kotlin
fun String.clength(): Int = this.length

fun main() {
    val names = listOf("John", "Alice", "Bob", "Charlie")

    val sortedNamesDesc = names.sortedByDescending(String::clength)

    println(sortedNamesDesc)
}

// Output
// [Charlie, Alice, John, Bob]
```

### Property References

Operator `::` juga dapat digunakan untuk mereferensikan sebuah properti. Kita dapat mengakses bagian dari properti tersebut seperti nama, fungsi _setter getter_, dll dengen operator. 

```kotlin
var num1 = 4
val num2 = 7

fun main(){

    println(::num1.get())
    println(::num1.name)

    ::num1.set(5)
    
    println(::num2.get())
    println(::num2.name)

    // Akan menampilkan error
    // ::num2.set(5)

}

// Output 
// 4
// num1
// 7
// num2
```

Ekspresi `::num1` akan dievaluasi ke dalam objek dengan **KMutableProperty**. Ini memungkinkan kita untuk membaca nilai dengan `get()`, menetapkan nilai dengan `set()`, dan mendapatkan nama properti dengan properti name. Sedangkan ekspresi `::num2` yang merupakan properti _immutable_ akan mengembalikan nilai dengan tipe **KProperty**, dimana hanya terdapat fungsi `get()` didalamnya.


<br/>