# Kotlin OOP (Object-Oriented Programming)

Sebagai hasil pengembangan bahasa dari Java, Kotlin memberi dukungan luas dalam pengembangan program berorientasi objek atau _object-oriented programming_ (OOP).  Beberapa hal yang akan kita pelajari pada materi Kotlin OOP ini yaitu : 

- Class
- Properties
- Lateinit & Lazy Property
- [Property Delegation](https://kotlinlang.org/docs/delegated-properties.html)
- Primary Constructor
- Secondary Constructor
- Visibility Modifiers
- Overloading
- Inheritances
- Abstract Class
- Interfaces
- Extensions
- Exception

Note : Untuk sub-materi yang diberi link tidak akan dijelaskan pada materi ini sehingga dapat dipelajari secara mandiri melalui link yang telah dilampirkan :)

## Class
Pendeklarasian _class_ pada Kotlin kurang lebih sama dengan pendeklarasian _class_ pada Java.

### Pendeklarasian Class
```kotlin
class Animal(){

    var name = "Kio"

    fun eat(){
        println("$name is Eating!")
    }

    fun sleep(){
        println("Sleepy...zzz")
    }

}
```

### Pemanggilan Class

```kotlin
fun main(){

    var cat = Animal()

    cat.eat()
    cat.sleep()

}

// Output 
// Kio is Eating!
// Sleepy...zzz

```

## Properties

Properti merupakan suatu hal yang pasti dimiliki oleh sebuah kelas dalam Kotlin. Pada kelas sebelumnya terdapat properti _name_. Properti merupakan sebuah variabel, dimana sebuah variabel dapat bernilai **_mutable_ (`var`)** atau **_read-only_ (`val`)**.

Pada dasarnya, sebuah variabel yang bernilai _mutable_ pada Kotlin akan memiliki fungsi _setter_ dan _getter_. Sedangkan variabel yang bernilai _read-only_ hanya akan memiliki fungsi _getter_. Namun, kita dapat melakukan _overrride_ kedua fungsi tersebut untuk dapat membuatnya secara manual.

```kotlin
class Cat(name: String){

    var name = name
        get() {
            println("Fungsi Getter")
            return field
        }
        set(value) {
            println("Fungsi Setter")
            field = value
        }

    fun eat(){
        println("$name is Eating!")
    }

    fun sleep(){
        println("Sleepy...zzz")
    }

}

fun main(){

    var cat = Cat("Kio")

    cat.name
    cat.eat()
    cat.sleep()

}

// Output 
// Fungsi Getter
// Fungsi Getter
// Kio is Eating!
// Sleepy...zzz
```

## Lateinit & Lazy Property

Lateinit dan Lazy Property digunakan ketika kita tidak ingin menginialisasi nilai sebuah variabel secara langsung. **Nilai variabel hanya akan diinialisasi ketika variabel tersebut dibutuhkan** dalam program kita. Hal ini digunakan ketika terjadi sebuah kondisi dimana nilai dari suatu variabel baru akan diinialisasi setelah mendapatkan data dari constructor atau karena kita perlu memakai variabel tersebut di fungsi lain atau yang biasa disebut dengan global variable.

### Lateinit Property

Lateinit property hanya bisa digunakan ketika disandingkan dengan **`var`**. Jika variabel akan digunaknan, variabel harus diinialisasi terlebih dahulu atau akan menyebabkan error. 

```
lateinit property [property name] has not been initialized
```

Contoh penggunaan _lateinit property_
```kotlin
lateinit var nim: String

fun main(){

    nim = "L0122158"

    println(nim)

}

// Output
// L0122158
```

Kita juga dapat melakukan pengecekan apakah variabel sudah terinisialisasi ataukah belum seperti pada kode di bawah ini :

```kotlin
    if(::nim.isInitialized){
        println(nim)
    }
    else {
        println("Not initialized.")
    }
```

### Lazy Property

Berkebalikan dengan _lateinit_, penggunaan _lazy property_ harus disandingkan dengan **`val`**. Hal ini dikarenakan _lazy property_ hanya dapat diinialisasi sebanyak 1 kali.

```kotlin
val angkatan : String by lazy { 
    "2023"
}

fun main(){

    println(angkatan)

}

// Output
// 2023
```

## Primary Constructor

Penulisan _primary constructor_ mirip dengan penulisan parameter pada fungsi. 

```kotlin
class Movie(genre: String)
```

Sama seperti parameter pada fungsi, _primary constructor_ juga dapat memiliki nilai default.

```kotlin
class Movie(genre: String = "Action")
```

### **Init Block**

Dalam Kotlin, terdapat **`init`** _block_ yang memungkinkan kita untuk menuliskan _property_ dalam _body class_ ketika kita menginialisasi kelas tersebut. Blok **`init`** juga memiliki fungsi lain, yaitu membantu kita melakukan **validasi** nilai terhadap properti tersebut sebelum diinialisasi.

```kotlin
class Movie(genre: String = "Action", duration: Int){

    val genre: String
    val duration: Int

    init {
        this.genre = genre
        this.duration = if(duration < 0) 0 else duration
    }

}

fun main(){

    val mov = Movie(duration = -1)

    println("${mov.genre} - ${mov.duration} hour(s)")

}

// Output
// Action - 0 hour(s)
```

## Secondary Constructor

Secondary constructor digunakan ketika kita ingin menginialisasi sebuah kelas dengan cara yang berbeda. Sebuah kelas dapat memiliki **lebih dari satu** _secondary constructor_. 

```kotlin
class Book(name: String, year: Int){

    var name: String
    var year: Int
    var isSelfPublished: Boolean

    init {
        this.name = name
        this.year = year
        this.isSelfPublished = false
    }

    constructor(name: String, year: Int, isSelfPublished: Boolean) : this(name, year) {
        this.isSelfPublished = isSelfPublished
    }

}

fun main() {

    var book1 = Book(name = "Kotlin Programming", year = 2025)
    var book2 = Book(name = "Android Development", year = 2025, isSelfPublished = true)

    println("Book 1 : ${book1.name} - ${book1.year} - ${book1.isSelfPublished}")
    println("Book 2 : ${book2.name} - ${book2.year} - ${book2.isSelfPublished}")

}

// Output
// Book 1 : Kotlin Programming - 2025 - false
// Book 2 : Android Development - 2025 - true
```

Jika kita tidak membuat sebuah _constructor_ secara manual, maka Kotlin akan secara otomatis membuat sebuah **_default constructor_** pada kelas tersebut.

## Visibility Modifiers
Hak akses suatu properti dalam Kotlin dapat kita tentukan sesuai sesuai kebutuhan. Dengan menentukan hak akses pada sebuah kelas, akses data dapat kita batasi. Dengan menentukan hak akses atau _visibility modifiers_, kita telah menerapkan salah satu pilar dalam OOP yaitu **_encapsulation_**  

Terdapat berbagai macam hak akses pada kotlin, yaitu 
- Public    : Dapat diakses dari **kelas manapun**.
- Private   : Hanya dapat diakses oleh properti pada **_scope_ yang sama**.
- Protected : Hanya dapat diakses oleh properti pada **kelas turunannya atau kelas itu sendiri**.
- Internal  : Hanya dapat diakses oleh properti yang **berada pada modul yang sama (satu modul)**.

```kotlin
class Number {
    private var angkaPrivate: Int = 10 // Variabel dengan hak akses private
    var angkaPublic: Int = 20 // Variabel dengan hak akses public
    protected var angkaProtected: Int = 30 // Variabel dengan hak akses protected
    internal var angkaInternal: Int = 40 // Variabel dengan hak akses internal

    // Fungsi untuk menampilkan nilai
    fun tampilkanNilai() {
        println("Nilai angkaPrivate (private): $angkaPrivate")
        println("Nilai angkaPublic (public): $angkaPublic")
        println("Nilai angkaProtected (protected): $angkaProtected")
        println("Nilai angkaInternal (internal): $angkaInternal")
    }
}

fun main() {
    val contoh = Number()

    // Kita bisa mengakses angkaPublic dan angkaInternal dari luar kelas
    println("Dari luar kelas:")
    println("Nilai angkaPublic (public): ${contoh.angkaPublic}")
    // println("Nilai angkaPrivate (private): ${contoh.angkaPrivate}") // Ini akan menghasilkan error karena angkaPrivate adalah private
    // println("Nilai angkaProtected (protected): ${contoh.angkaProtected}") // Ini akan menghasilkan error karena angkaProtected tidak dapat diakses secara langsung dari luar kelas
    println("Nilai angkaInternal (internal): ${contoh.angkaInternal}")

    // Kita juga bisa memanggil method yang menggunakan angkaPrivate, angkaPublic, angkaProtected, dan angkaInternal di dalamnya
    println("\nMemanggil method tampilkanNilai() dari luar kelas:")
    contoh.tampilkanNilai()
}

// Output
// Dari luar kelas:
// Nilai angkaPublic (public): 20
// Nilai angkaInternal (internal): 40

// Memanggil method tampilkanNilai() dari luar kelas:
// Nilai angkaPrivate (private): 10
// Nilai angkaPublic (public): 20
// Nilai angkaProtected (protected): 30
// Nilai angkaInternal (internal): 40
```

## Overloading
Overloading merupakan penerapan dari salah satu pilar OOP yaitu _**polymorphism**_. Dengan overloading, kita dapat mendefinisikan **beberapa fungsi atau _constructor_ dengan nama yang sama pada satu kelas, tetapi dengan parameter yang berbeda**. Hal ini memungkinkan kita untuk beberapa versi dari fungsi yang melakukan tugas yang sama tetapi dengan jenis atau jumlah argumen yang berbeda.

Konsep overloading ini sangat berguna atau _powerful_ dalam pembuatan program kalkulator.

```kotlin
class MathOperations {
    // Fungsi untuk menjumlahkan dua bilangan bulat
    fun jumlah(a: Int, b: Int): Int {
        return a + b
    }

    // Overloaded function to add three integers
    // Fungsi overload untuk menjumlahkan tiga bilangan bulat
    fun jumlah(a: Int, b: Int, c: Int): Int {
        return a + b + c
    }

    // Overloaded function to add two doubles
    // Fungsi overload untuk menjumlahkan dua bilangan double
    fun jumlah(a: Double, b: Double): Double {
        return a + b
    }
}

fun main() {
    val operasi = MathOperations()

    // Memanggil fungsi jumlah dengan dua argumen integer
    val hasil1 = operasi.jumlah(5, 3)
    println("Hasil penjumlahan dari dua bilangan integer: $hasil1")

    // Memanggil fungsi jumlah dengan tiga argumen integer
    val hasil2 = operasi.jumlah(2, 4, 6)
    println("Hasil penjumlahan dari tiga bilangan integer: $hasil2")

    // Memanggil fungsi jumlah dengan dua argumen double
    val hasil3 = operasi.jumlah(2.5, 3.5)
    println("Hasil penjumlahan dari dua bilangan double: $hasil3")
}

// Output
// Hasil penjumlahan dari dua bilangan integer: 8
// Hasil penjumlahan dari tiga bilangan integer: 12
// Hasil penjumlahan dari dua bilangan double: 6.0
```

## Inheritances
Inheritance atau pewarisan adalah salah satu pilar yang ada dalam 4 pilar OOP. Dengan menerapkan _inheritance_, kita dapat menghemat kode dan meminimalisir terjadinya _boilerplate_ pada kode yang kita buat.

```kotlin
// diberi "open" agar dapat dilakukan inheritance/pewarisan
open class Fish(){

    var name = "Kio"

    open fun eat(){
        println("$name is Eating!")
    }

    open fun sleep(){
        println("Sleepy...zzz")
    }

}

class Shark(name: String) : Fish() {

    init {
        super.name = name
    }

    override fun eat(){
        println("Yumm... $name is eating lots of fishes")
    }

    override fun sleep(){
        println("$name is sleeping underwater...")
    }

    fun swim(){
        println("$name is exploring the sea!!")
    }

}

fun main() {
    val shark = Shark("Rhoa")
    shark.eat()  
    shark.sleep()  
    shark.swim()
}

//  Output
// Yumm... Rhoa is eating lots of fishes
// Rhoa is sleeping underwater...
// Rhoa is exploring the sea!!
```

### Overriding
Overriding adalah konsep dalam pemrograman berorientasi objek di mana kelas turunan (_subclass_) dapat **mengganti implementasi metode yang sama** dari kelas dasar (_superclass_). Dalam Kotlin, Anda menggunakan kata kunci **`override`** sebelum metode yang ingin Anda ganti. Ini memungkinkan kelas turunan menyediakan perilaku khusus yang sesuai dengan kebutuhan kelas tersebut. Penting untuk memperhatikan bahwa nama, tipe, dan parameter metode harus cocok persis dengan metode yang ingin dioverride di kelas dasar.

```kotlin
override fun eat(){
    println("Yumm... $name is eating lots of fishes")
}
```

## Abstract Class
Abstract class adalah sebuah kelas yang **tidak dapat diinstansiasi secara langsung** dan dapat memiliki metode abstrak (_abstract method_). Metode abstrak adalah metode yang tidak memiliki implementasi di dalam kelas abstrak, tetapi harus diimplementasikan oleh kelas turunannya (_subclass_). Abstraksi digunakan ketika kita ingin memiliki kelas dasar yang menyediakan kerangka kerja umum, tetapi membiarkan detail implementasi khusus ditentukan oleh kelas turunannya.

```kotlin
abstract class Vehicle {
    abstract fun start()

    abstract fun stop()

    fun accelerate() {
        println("Accelerating...")
    }
}

class Motorcycle : Vehicle() {
    override fun start() {
        println("Motorcycle started")
    }

    override fun stop() {
        println("Motorcycle stopped")
    }
}

fun main() {

    val motorcycle = Motorcycle()
    motorcycle.start() 
    motorcycle.accelerate() 
    motorcycle.stop() 
}

// Output
// Motorcycle started
// Accelerating...
// Motorcycle stopped
```

## Interfaces
Interface adalah konsep mengenai sifat umum yang akan digunakan oleh suatu kelas untuk memiliki sifat tersebut. Interface sangat mirip dengan _abstract class_, namun tanpa sebuah properti deklarasi dan fungsi yang dideklarasikan tanpa isi. Interface bertujuan untuk diimplementasikan oleh sebuah kelas. Pengimplementasian _interface_ pada sebuah kelas **harus meng-_override_ seluruh properti dan fungsi** sekaligus memberikan definisi terhadap isi fungsi yang terdapat dalam _interface_ tersebut.

```kotlin
interface Shape {
    val area: Double
    
    fun calculateArea(): Double
}

// Kelas Circle yang mewarisi dari interface Shape
class Circle(val radius: Double) : Shape {
    // Implementasi properti area dari interface Shape
    override val area: Double
        get() = Math.PI * radius * radius
    
    // Implementasi metode calculateArea dari interface Shape
    override fun calculateArea(): Double {
        return Math.PI * radius * radius
    }
}

fun main() {
    val circle = Circle(5.0)
    println("Area of the circle: ${circle.calculateArea()}")

}

// Output
// Area of the circle: 78.53981633974483
```

## Extensions

Pada Kotlin, kita dimungkinkan untuk menambahkan fungsi baru pada sebuah kelas tanpa harus mewarisi kelas tersebut. Terdapat 2 jenis _extensions_ yang didukung oleh Kotlin, yaitu **Extension Function** dan **Extension Properties**.

### Extension Function
Extension function berfungsi untuk **menambahkan fungsi baru**. Extension Function dideklarasikan dengan menentukan terlebih dahulu _receiver type_, kemudian nama fungsi yang dipisahkan oleh tanda titik (.). 

```kotlin
fun Int.dollarToRupiah(): String {
    val rupiah = this * 15700
    return "Rp.$rupiah"
}

fun main() {
    val price = 15
    println("Harga dalam Rupiah: ${price.dollarToRupiah()}") 
}

// Output
// Harga dalam Rupiah: Rp.235500
```

### Extension Property
Selain menambah fungsi baru, Kotlin juga mendukung extension untuk menambah sebuah properti baru pada sebuah kelas tanpa harus menyentuh kode di dalam kelas tersebut. Cara pendeklarasiannya pun sama seperti _extension function_, kita perlu menentukan terlebih dahulu _receiver type_, kemudian nama properti yang dipisahkan oleh tanda titik (.). 

```kotlin
val String.lengthWithSpaces: Int
    get() = this.length + this.count { it == ' ' }

fun main() { 
    val text = "Hello World"
    println("Panjang string termasuk spasi: ${text.lengthWithSpaces}")
}

// Output
// Panjang string termasuk spasi: 12
```

## Exception

Exception adalah suatu kondisi abnormal atau kejadian yang tidak diharapkan yang terjadi saat program sedang berjalan. Ketika exception terjadi, program biasanya akan berhenti atau mengalami gangguan jika tidak ditangani dengan benar. Penanganan exception memungkinkan program untuk merespons dengan baik terhadap kesalahan dan melanjutkan eksekusi program dengan normal.

Dalam Kotlin, exception diwakili oleh kelas-kelas yang mewarisi dari kelas _Throwable_, baik itu kelas bawaan seperti _Exception_, _RuntimeException_, atau kelas buatan pengguna. Kotlin menggunakan blok `try`, `catch`, dan `finally` untuk menangani exception.

```kotlin
fun divide(a: Int, b: Int): Int {
    return a / b
}

fun main() {
    try {
        val result = divide(10, 0)
        println("Hasil pembagian: $result") // Baris ini tidak akan pernah dijalankan
    } catch (e: ArithmeticException) {
        println("Terjadi kesalahan: ${e.message}") // Menampilkan pesan kesalahan
    } finally {
        println("Proses selesai") // Selalu dijalankan, terlepas dari exception atau tidak
    }
}

// Output
// Terjadi kesalahan: / by zero
// Proses selesai
```

<br/>
