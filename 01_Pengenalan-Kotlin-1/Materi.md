# Kotlin Fundamental

Kotlin adalah bahasa pemrograman modern yang didukung oleh Google dan banyak digunakan untuk pengembangan aplikasi Android, serta berbagai jenis pengembangan perangkat lunak lainnya.

```kotlin
// main Function
fun main(){
    println("Hello World!")
}
```

Berikut cara pemberian comment pada Kotlin

```kotlin
// single line comment

/*
    multi line comment
    Hello World!
*/
```

## Data Types & Variables

Dalam bahasa Kotlin juga terdapat variables. Pada sebuah variable akan dibutuhkan keyword berupa var atau val, identifier, type, dan initialization. Berikut ini struktur dari sebuah variable.

```kotlin
var indentifier : Type = initialization
```

### Deskripsi

- **var**: untuk mendeklarasikan sebuah variabel yang nilainya dapat diubah (mutable)
- **val**: untuk mendeklarasikan sebuah variabel yang nilainya tidak dapat diubah (immutable) setelah diberi nilai pertama kali
- **Identifier**: nama yang diberikan untuk variabel tersebut
- **Type**: tipe data dari variabel tersebut
- **Initialization**: nilai awal yang diberikan kepada variabel pada saat deklarasi

Berikut ini adalah contoh dari penggunaan variable dan data type.

```kotlin
var carName : String = "Toyota 86"
val carPrice : Int = 875000000

carPrice = 400000000 // Tidak bisa
```

## Char

Tipe data yang digunakan untuk merepresentasikan sebuah karakter tunggal dalam kode Unicode. Berikut ini adalah contoh impelementasi dari penggunaan 'Char'

```kotlin
val huruf : Char = 'A'
val simbol = '$'
val kata : Char = 'informatika' // Tidak bisa
```

## String

String adalah tipe data yang digunakan untuk merepresentasikan urutan karakter dalam sebuah program komputer. Dalam Kotlin, String direpresentasikan dengan kata kunci String. Sebuah String dapat berisi satu atau lebih karakter, seperti huruf, angka, simbol, atau kombinasi dari semuanya

```kotlin
val kata : String = "Halo"
val kalimat : String = "Ini adalah contoh kalimat."
```

#### Escaped String

Escaped String memungkinkan kita untuk mengurangi ambiguitas nilai yang berada di dalam sebuah String. Contohnya seperti berikut ini.

```kotlin
val statement = "Nisa \"cantik\" hari ini!"
```

Karakter-karakter lain yang dapat digunakan untuk melakukan escaped sebagai berikut.

- \t: menambah tab ke dalam teks.
- \n: membuat baris baru di dalam teks.
- \’: menambah karakter single quote kedalam teks.
- \”: menambah karakter double quote kedalam teks.
- \\\\: menambah karakter backslash kedalam teks.

#### Raw String

Raw String yang memungkinkan untuk menuliskan multiline dan arbitrary text. Untuk mendefinisikan raw string, dapat menggunakan tiga tanda kutip (triple quotes): """. Ketika ingin membuat beberapa baris String, biasanya kita melakukan escaped terhadap String dengan memanfaatkan karakter escape \n seperti berikut.

```kotlin
fun main() {
    val rawString = """
        Ini adalah contoh raw string dalam Kotlin.
        Dalam raw string, Anda dapat menuliskan teks sebagaimana adanya,
        tanpa perlu melakukan escape terhadap karakter khusus seperti \n (baris baru) atau \t (tab).
        Raw string sangat berguna saat Anda perlu menuliskan teks panjang atau teks yang mengandung banyak karakter escape.
    """
    println(rawString)
}

```

## String Template

String template adalah fitur yang memungkinkan penggabungan nilai variabel ke dalam string. Nilai variabel tersebut dapat langsung dimasukkan ke dalam string tanpa perlu melakukan konversi tipe data atau penggabungan string secara manual. Ini membuat kode menjadi lebih mudah dibaca dan ditulis. Dalam Kotlin, string template ditandai dengan penggunaan karakter dollar "$" sebelum nama variabel yang ingin dimasukkan ke dalam string. Jika ingin menyertakan ekspresi yang lebih kompleks, Anda dapat menggunakan kurung kurawal "${}" untuk membatasi ekspresi. Berikut ini adalah contoh implementasinya.

```kotlin
fun main() {
    val name = "Budi"
    val age = 30
    val message = "Halo, nama saya $name dan saya berusia $age tahun."
    println(message)

    val lengthMessage = "Panjang nama saya adalah ${name.length} karakter."
    println(lengthMessage)
}

```

## If Expression

Dalam Kotlin, if digunakan untuk menguji suatu kondisi dan menjalankan sebuah proses jika hasil evaluasi kondisi tersebut bernilai true. Pada dasarnya, if expressions diwakili oleh kata kunci if. Ini akan mengeksekusi sebuah statement atau expression jika hasil evaluasi dari kondisi yang diberikan bernilai true. Jika kondisi tersebut bernilai false, proses yang ditentukan akan dilewatkan. Dalam penggunaan yang sederhana, if dapat digunakan untuk membandingkan nilai menggunakan operator perbandingan seperti greater than (>). Contoh penggunaan if/else sebagai berikut.

```kotlin
// Contoh penggunaan if expression dengan else if
val score = 75
val grade = if (score >= 90) {
    "A"
} else if (score >= 80) {
    "B"
} else if (score >= 70) {
    "C"
} else if (score >= 60) {
    "D"
} else {
    "E"
}
println("Nilai anda: $grade")

```

## Boolean

Boolean merupakan tipe data yang merepresentasikan nilai kebenaran, yang hanya memiliki dua nilai: true atau false. Boolean digunakan dalam banyak konteks dalam pemrograman, seperti pengontrol alur program (seperti dalam pernyataan if), pengembalian nilai dari fungsi yang mengembalikan kondisi, dan banyak lagi.

### Conjunction and AND (&&)

Konjungsi atau AND adalah operator logika yang menghasilkan nilai true hanya jika kedua operandnya bernilai true. Jika salah satu atau kedua operand bernilai false, hasilnya akan false. Contoh

- true && false atau false && true atau false && false semuanya menghasilkan false

```kotlin
val cafeOpen = 10
val cafeClosed = 22
val now = 18

val isOpen = if (now >= cafeOpen && now <= cafeClosed){
    true
} else {
    false
}

print("Cafe is open : $isOpen")

/*
    Output : Cafe is open : false
 */
```

### Disjunction and OR (||)

Disjungsi atau OR adalah operator logika yang menghasilkan nilai true jika setidaknya salah satu dari operandnya bernilai true. Hanya ketika kedua operand bernilai false, hasilnya akan false.
Contoh:

- true || true atau true || false atau false || true menghasilkan true
- false || false menghasilkan false.

```kotlin
val isClose = now < cafeOpen || now > cafeClosed

print("Cafe is closed : $isClose")
/*
     Output : Cafe is closed : true
 */
```

### Negation and NOT (!)

Negasi atau NOT adalah operator logika yang mengubah nilai operandnya.
Jika operand awalnya bernilai true, maka setelah negasi akan menjadi false, dan sebaliknya.
Contoh:

- !true menghasilkan false
- !false menghasilkan true.

```kotlin
if (!isOpen) {
    print("Cafe is closed")
} else {
    print("Cafe is open")
}

/*
    Output : Cafe is open
*/
```

## Numbers

Di Kotlin, tipe data Number disimpan dalam berbagai cara. Beberapa tipe bawaan yang mewakili Numbers adalah Double, Long, Int, Short, dan Byte. Setiap tipe data Number memiliki ukuran (dalam satuan Bit) yang berbeda-beda, tergantung pada besaran nilai yang dapat disimpan. Dengan tipe data Number ini, Kotlin memberikan fleksibilitas dan kekuatan untuk bekerja dengan berbagai jenis data numerik dalam aplikasi.

#### Int (32 Bit)

Int dapat menyimpan data range -2^31 sampai +2^31-1

```kotlin
val intNumber = 99
```

#### Long (64 Bit)

Long dapat menyimpan data range -2^63 sampai +2^63-1

```kotlin
val longNumber : Long = 256
// atau
val longNumberL = 256L
```

#### Short (16 Bit)

```kotlin
val shortNumber : Short = 7
```

#### Byte (8 Bit)

```kotlin
val byteNumber = 0b11010010
```

#### Double (64 Bit)

Double digunakan untuk menyimpan nilai numerik pecahan sampai dengan maksimal 15-16 angka di belakang koma.

```kotlin
val doubleNumber : Double = 7.6
```

#### Float (32 Bit)

Sama seperti Double, namun memiliki ukuran yang lebih kecil, yakni hanya sampai 6-7 angka di belakang koma.

```kotlin
val floatNumber : Float = 0.4354535783f
// yang terbaca 0.4354535
```

## Arrays

Arrays adalah struktur data yang digunakan untuk menyimpan kumpulan elemen data dalam satu variabel. Dalam array, setiap elemen memiliki indeks numerik yang digunakan untuk mengaksesnya. Indeks dimulai dari 0 untuk elemen pertama, 1 untuk elemen kedua, dan seterusnya. Ukuran array itu fix, artinya setelah array dibuat, kita tidak bisa menambah atau mengurangi elemen di dalamnya. Berikut ini adalah contoh penggunaan array.

```kotlin
val array = arrayOf(1,3,5,7)
val mixArray = arrayOf(1,3,5,7, "Informatika", true)
val intArray = intArrayOf(1,3,5,7)
val intSpecificArray : IntArray = intArrayOf(1,3,5,7)
val intCastedArray = arrayOf<Int>(1,3,5,7) // not primitive
val intCastedArray2 = arrayOf(1,3,5,7).toIntArray()
```

Dapat membuat Array dengan tipe data primitif dengan fungsi berikut ini.

- intArrayOf(): IntArray
- booleanArrayOf() : BooleanArray
- charArrayOf() : CharArray
- longArrayOf() : LongArray
- shortArrayOf() : ShortArray
- byteArrayOf() : ByteArray

#### Multi-dimensional Array

Multi-dimensional array adalah array yang memiliki lebih dari satu dimensi. Dalam Kotlin, multi-dimensional array dapat dibuat dengan menggunakan fungsi arrayOf(), seperti berikut ini.

```kotlin
val multiArray = arrayOf(arrayOf(1,2,3), arrayOf(4,5,6), arrayOf(7,8,9))
```

Ukuran dimensi harus konsisten agar semua elemen dapat diakses dengan benar. Berikut adalah contoh yang salah, tapi tidak menghasilkan error ketika inisisalisasi. Namun, akan menghasilkan error ketika diakses.

```kotlin
var multiArray = arrayOf(arrayOf<Any>(1,2,3,arrayOf(1,2)), arrayOf("asd",6), arrayOf(7,8,9)) // no error

print(multiArray[0][3][1]) // error
```

## Nullables Types

NPE (NullPointerException) adalah sebuah error yang terjadi ketika program mencoba untuk mengakses sebuah variabel yang bernilai null. NPE dapat menyebabkan program crash atau berhenti berjalan. Hal ini dapat terjadi karena beberapa alasan, seperti:

1. Variabel belum diinisialisasi.
2. Variabel diinisialisasi dengan nilai null.
3. Nilai variabel diubah menjadi null.
4. Akses properti atau method pada variabel null.

```kotlin
val text : String = null // compiler time error : text berekspektasi sebuah String, bukan null
```

Jika ingin membuat sebuah objek dapat bernilai null, harus ditambahkan tanda **?** setelah menentukan tipe data dari objek tersebut seperti berikut ini.

```kotlin
val text : String? = null // ready to go
```

Kemudian, tidak bisa dilakukan pengaksesan yang ditandai nullable secara langsung.

```kotlin
val text : String? = null
val textLength = text.length // compile time error
```

Kode di atas akan menghasilkan error berikut ini.
Error:(4, 26) Kotlin: Only safe (?.) or non-null asserted (!!.) calls are allowed on a nullable receiver of type String?

Cara mengatasi hal di atas adalah dengan menggunakan cara tradisional, yaitu dengan menggunakan **if/else** dimana ini memanfaatkan fitur **smart cast** pada Kotlin.

```kotlin
var text2 : String? = "Informatika"
if(text2 != null){ // smart cast
    print(text2.length) // It works now!
}
```

Atau dengan menggunakan **safe call** atau **elvis operator** pada penjelasan di bawah ini.

## Safe Calls and Elvis Operators

#### Safe Calls

Safe calls operator (?.) memungkinkan kita untuk mengakses properti atau memanggil method pada variabel nullable dengan aman. Operator ini akan mengembalikan null jika variabelnya null, dan tidak akan menyebabkan NPE.

```kotlin
val carName : String? = null
println(carName?.length) // Output: null
```

Dengan adanya safe call, compiler akan melewatkan proses jika objek tersebut bernilai null.

#### Elvis Operators (condition ? then : else)

Elvis operator (?:) memungkinkan kita untuk memberikan nilai default jika variabel nullable bernilai null. Operator ini akan mengembalikan nilai variabel jika tidak null, dan nilai default jika null. Berikut ini adalah contoh penggunaan dari **Elvis Operators**.

```kotlin
val carName : String? = null
val carNameLength = carName?.length ?: 5
```

Kode di atas sama seperti menggunakan **if/else** berikut ini.

```kotlin
val carNameLength = if(carName != null) carName.length else 5
```

#### Non-null Assertion (!!)

Terdapat **non-null assertion ( !! )** seperti berikut.

```kotlin
val carName : String? = null
val carNameLength = carName!!.length
```

Hal ini tidak disarankan karena akan memaksa sebuah objek menjadi **non-null** sehingga ketika objek tersebut bernilai null tetap akan mendapatkan **NullPointerException**

## Functions

Function dalam Kotlin adalah blok kode yang dapat digunakan kembali untuk melakukan tugas tertentu. Ini mirip seperti definisi fungsi pada umumnya, namun Kotlin memiliki beberapa fitur dan keunikan tersendiri dalam hal fungsi.

```kotlin
fun functionName(param1: Type1, param2: Type2, ...): ReturnType {
    return result
}
```

Berikut ini adalah contoh function yang memiliki nilai kembalian.

```kotlin
fun setProduct(name:String, price:Int):String{
    return "Product is $name and price is $price"
}
```

Dikarenakan hanya terdapat satu expression pada kode di atas, maka dapat dibuat seperti ini juga.

```kotlin
fun setProduct(name:String, price:Int):String = "Product is $name and price is $price"
```

Contoh pemanggilan function.

```kotlin
fun main(){
    val product = setProduct("Laptop", 15_000_000)
    println(product)
}

/*
    output:
    Product is Laptop and price is 15000000
*/
```

Tipe data dari nilai return dapat dihilangkan jika terdapat **single expression** pada function tersebut.

```kotlin
fun setProduct(name:String, price:Int) = "Product is $name and price is $price"
```

Namun, jika terdapat **multi expression** pada function tersebut, maka tipe data dari nilai return tidak dapat dihilangkan. Begitu pula untuk tipe data dari **parameter**, harus dituliskan secara **eksplisit**.

Dalam kasus function yang tidak mempunyai nilai return, maka tipe data dari nilai return seharusnya adalah **Unit**. Namun, tipe data dari nilai return **Unit** dapat dihilangkan (opsional).

```kotlin
fun printProduct(name:String, price:Int){
    println("Product is $name and price is $price")
}

fun printProduct(name:String, price:Int):Unit{
    println("Product is $name and price is $price")
}
```

## Named and Default Argument

#### Named Argument

Named argument memungkinkan Anda untuk memanggil fungsi dengan menyebutkan nama parameternya secara eksplisit. Ini berguna ketika:

- Urutan parameter tidak jelas.
- Fungsi memiliki banyak parameter.
- Ingin memberikan nilai hanya untuk beberapa parameter, dan ingin melewatkan parameter lain.

```kotlin
fun greet(name: String, age: Int) {
  println("Halo, $name! Kamu berusia $age tahun.")
}

greet(age = 20, name = "Budi") // calling function using named argument

```

#### Default Argument

Default argument memungkinkan Anda untuk menetapkan nilai default untuk parameter fungsi. Ini berguna ketika:

- Nilai parameter sering kali sama.
- Ingin membuat fungsi lebih mudah digunakan.

```kotlin
fun greet(name: String, age: Int = 18) {
  println("Halo, $name! Kamu berusia $age tahun.")
}

greet("Budi") // Hanya memberikan nilai untuk parameter `name`

```

## Vararg (Variable Argument)

Dengan vararg sebuah fungsi dapat memiliki jumlah parameter berdasarkan jumlah argumen yang kita masukkan ketika fungsi tersebut dipanggil. Contoh dari penggunaan vararg adalah sebagai berikut.
Berikut ini merupakan declaration dari Vararg.

```kotlin
fun myFunction(vararg numbers: Int) {
  // Code
}

```

Berikut contoh impelemtasinya.

```kotlin
fun sum(vararg numbers: Int): Int {
  var total = 0
  for (number in numbers) {
    total += number
  }
  return total
}

val result = sum(1, 2, 3, 4, 5)
println(result) // Output: 15

```

#### Aturan pada Vararg Arguments

1. Tidak diizinkan terdapat dua parameter bertanda **vararg** dalam satu function, seperti berikut ini.

```kotlin
fun sumNumbers(vararg number: Int, vararg number2: Int)
```

2. Jika ingin menambahkan parameter baru tanpa keyword **vararg**, maka parameter yg ditandai dengan **vararg** sebaiknya pada posisi terakhir.

```kotlin
fun main(){
    setData("Kotlin", 20,20,30)
}

fun setData(name:String, vararg number : Int):Int{
    ...
}
```

Bagaimana jika ingin menempatkan parameter yg ditandai **vararg** pada posisi pertama? perhatikan kode berikut ini.

```kotlin
fun main(){
    setData(20,20,30, name = "Kotlin")
}

fun setData(vararg number : Int, name:String):Int{
    ...
}
```

#### Spread Operator

Spread operator adalah operator yang digunakan untuk mengeluarkan/unpack nilai dari sebuah array atau collection dan memasukkannya ke dalam sebuah fungsi yang memiliki parameter vararg. Tanpa spread operator, function akan menerima parameter sebagai satu array. Berikut ini adalah contoh penggunaan dari spread operator.

```kotlin
fun main(){
    val numbers = intArrayOf(20,20,30)
    setData(*numbers, name = "Kotlin")
}

fun setData(vararg number : Int, name:String):Int{
    ...
}
```
