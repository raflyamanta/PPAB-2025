# Special Class

Special Class adalah yang memiliki fitur atau **karakteristik tertentu** yang tidak tersedia di _class_ biasa (reguler). 

Beberapa _special class_ dalam Kotlin telah kita pelajari sebelumnya, yaitu Abstract Class dan Interface. Selain itu, terdapat berbagai macam _special class_ lain, yaitu :
- Data Class    : Class yang digunakan untuk menyimpan data.
- [Nested Class](https://kotlinlang.org/docs/nested-classes.html)   : membuat Class di dalam Class tanpa akses ke kelas induk.
- [Inner Class](https://kotlinlang.org/docs/nested-classes.html#inner-classes) : membuat Class di dalam Class dengan akses ke kelas induk.
- Enum Class : tipe data dengan sekumpulan nilai konstan yang telah ditentukan.
- [Sealed Class](https://kotlinlang.org/docs/sealed-classes.html) : digunakan untuk pembatasan hierarki class.

## Data Class

Kotlin memperkenalkan konsep **data class**, sebuah kelas sederhana yang dapat berfungsi sebagai **data container**. Kelas ini tidak memiliki logika dan tidak memiliki fitur selain menangani data. Berbeda dengan kelas biasanya, _data class_ tidak membutuhkan banyak kode untuk mengelola sebuah data.

```kotlin
data class Mahasiswa(val nama: String, val nim: String)
```

Kompiler akan secara otomatis menghasilkan `constructor`, `toString()`, `equals()`, `hashCode()`, `copy()`, dan fungsi `componentN` hanya dengan satu baris kode di atas. Hal ini membuat kode kita lebih bersih dan lebih mudah dibanding ketika kita menuliskan kode secara manual. 

Terdapat beberapa hal yang perlu diperhatikan ketika membuat sebuah data class, yaitu : 
- Konstruktor utama pada kelas tersebut **harus memiliki setidaknya satu parameter**.
- Semua konstruktor utama **perlu dideklarasikan** sebagai `val` atau `var`;
- Sebuah data class **tidak dapat memiliki _modifier_** berupa `abstract`, `open`, `sealed`, atau `inner`.

### **toString()** pada Data Class

Misalkan kita memiliki dua buah _class_, satu _class_ biasa dan 1 _data class_.

```kotlin
class Student(val name: String, val nim: String)

data class Mahasiswa(val name: String, val nim: String)
```

Kita akan mencoba menampilkan kedua objek yang dibuat dari kedua _class_ tersebut dengan menggunakan `println`.

```kotlin
fun main(){
    val student = Student("Rui", "M0522007")
    val mahasiswa = Mahasiswa("Rui", "M0522007")

    println(student)
    println(mahasiswa)
}

// Output
// Student@65b54208
// Mahasiswa(name=Rui, nim=M0522007)
```

Dapat dilihat bahwa objek student menghasilkan teks Student@65b54208, di mana Student adalah nama kelas itu sendiri, dan @65b54208 adalah memory address dari kelas tersebut. Namun, objek data maahsiswa menghasilkan teks Mahasiswa(name=Rui, nim=M0522007), yaitu nama kelas bersama dengan semua properti dan nilai dari properti tersebut. 

Hal tersebut menunjukkan bahwa kita dapat mengetahui informasi yang terdapat di `mahasiswa` hanya dengan melihat nilai dari properti yang ada. Kondisi ini dapat terjadi dikarenakan **data class** akan secara otomatis menghasilkan fungsi `toString()` didalamnya. Tanpa kelebihan tersebut, kita harus membuat fungsi `toString()` secara manual untuk dapat mengetahui semua informasi yang ada dalam suatu _class_. 

### **equals()** pada Data Class

Ketika kita menggunakan _data class_, kita dapat membandingkan dua buah objek hanya dengan menggunakan fungsi `equals`. Hal yang dibandingkan pada fungsi ini adalah informasi dari objek tersebut.

```kotlin
fun main(){
    val mahasiswa = Mahasiswa("Rui", "M0522007")
    val mahasiswa2 = Mahasiswa("Rui", "M0522007")
    val mahasiswa3 = Mahasiswa("Atya", "M0521003")

    println(mahasiswa.equals(mahasiswa2))
    println(mahasiswa.equals(mahasiswa3))

    // Output : 
    // true
    // false
}
```

### **copy()** pada Data Class

Data Class juga memungkinkan kita untuk melakukan duplikat pada sebuah objek dengan memperbarui nilai pada propertinya.

```kotlin
fun main(){
    val mahasiswa = Mahasiswa("Rui", "M0522007")

    println(mahasiswa.copy(name = "Kio"))
    println(mahasiswa.copy(name = "Rhode", nim = "M0511065"))
}

// Output
// Mahasiswa(name=Kio, nim=M0522007)
// Mahasiswa(name=Rhode, nim=M0511065)
```

## Enum Class

Enum class dalam Kotlin adalah tipe data khusus yang digunakan untuk mendefinisikan kumpulan nilai tetap (enumerasi). Enum class biasanya digunakan untuk merepresentasikan himpunan nilai yang tetap dan terbatas.

```kotlin
enum class DayOfWeek {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

fun printDay(day: DayOfWeek) {
    println("Today is $day")
}

fun main() {
    val today = DayOfWeek.MONDAY
    printDay(today) 
}

// Output
// Today is MONDAY
```

<br/>