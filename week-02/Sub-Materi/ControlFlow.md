# Control Flow

Dalam melakukan pemrograman, kita perlu tau **alur** dari sebuah program yang kita buat, maka dari itu diperlukan adanya Control Flow. Control Flow merupakan sebuah cara seorang pemrogram **mengatur alur dari sebuah program berdasarkan kondisi yang terjadi** saat program tersebut berjalan. Terdapat berbagai macam Control Flow, yaitu :

- When Expression
- While & Do-While Expression
- Range & For Loop 
- Break & Continue

## When Expression
**When Expression** merupakan bentuk lain dari If Expression, dimana kita dapat **mengubah alur program melalui nilai** dari sebuah variabel/_expression_.

```kotlin
fun main(){
    var condition = 0;

    when(condition){
        1 -> println("True")
        0 -> {
            println("False")
            condition += 1
            println(condition)
        }
        else -> println("Unknown")
    }
}

// Output
// False
// 1
```

## While & Do-While Expression
While & Do-While merupakan sebuah bentuk ekspresi perulangan. Kedua _expression_ tersebut akan menjalankan blok kode yang sama berulang kali hingga kondisi sudah tidak terpenuhi atau menunjukkan nilai _false_.

### While

Dalam perulangan While, pengecekan dilakukan terlebih dahulu baru blok kode dalam ekspresi _while_ dijalankan. 

```kotlin
fun main(){

    var num = 6

    while(num <= 5){
        println("Kotlin is fun!!")
        num++
    }

}

// Output : (Kosong)
```

### Do-While

Dalam perulangan Do-While, blok kode dalam ekspresi _do-while_ dijalankan terlebih dahulu baru pengecekan dilakukan. 

```kotlin
fun main(){

    var num = 6

    do {
        println("Kotlin is fun!!")
        num++
    }
    while(num <= 5)

}

// Output
// Kotlin is fun!!
```

## Range 
Range digunakan untuk membuat sebuah variabel yang berisikan deretan angka tertentu. Penggunaan Range pada Kotlin mirip dengan penggunaan Range pada GNU Octave/Matlab.

```kotlin
fun main(){

    var myInt = 1..5            // 1 2 3 4 5
    var myInt_2 = 1..5 step 2   // 1 3 5
    var myInt_3 = 1.rangeTo(7)  // 1 2 3 4 5 6 7

}
```

## For Loop

For loop pada Kotlin dapat dilakukan dengan beberapa cara, yaitu for loop biasa, for loop dengan menampilkan index, dan for loop dengan menggunakan foreach. Foreach merupakan bentuk dari Lambda Expression yang akan dibahas pada materi selanjutnya. 

```kotlin
fun main(){

    var myInt = 1..5
    var myInt_2 = 1..5 step 2
    var myInt_3 = 1.rangeTo(7)

    // for loop menggunakan ranges
    print("myInt : ")
    for(num in myInt){
        print("$num ")
    }
    println()

    // for loop menggunakan withIndex()
    print("myInt_2 : ")
    for((idx, num) in myInt_2.withIndex()){
        print("$num - $idx  ")
    }
    println()

    // for loop menggunakan foreach
    print("myInt_3 : ")
    myInt_3.forEach { num -> 
        print("$num ")
    }
    println()

}

// Output 
// myInt : 1 2 3 4 5 
// myInt_2 : 1 - 0  3 - 1  5 - 2
// myInt_3 : 1 2 3 4 5 6 7
```

## Break 
Perintah _break_ digunakan untuk keluar dari perulangan jika suatu kondisi terpenuhi. Perintah ini sangat berguna ketika kita ingin melakukan pengecekan suatu kondisi yang berakibat pada berhentinya looping dan lanjut ke blok kode berikutnya.

```kotlin
fun main(){

    var myInt = 1..5 step 2

    print("myInt : ")
    for(num in myInt){
        if(num == 3) break
        print("$num ")
    }
    println()

}

// Output
// myInt : 1 
```

## Continue

Perintah _continue_ digunakan untuk melewatkan proses iterasi pada perulangan jika suatu kondisi terpenuhi dan berlanjut ke iterasi selanjutnya. 

```kotlin
fun main(){

    var myInt = 1..5 step 2

    print("myInt : ")
    for(num in myInt){
        if(num == 3) continue
        print("$num ")
    }
    println()

}

// Output 
// myInt : 1 5 
```

<br/>
