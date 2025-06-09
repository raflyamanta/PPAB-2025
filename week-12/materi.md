# Data Persistence 2
Dalam pengembangan aplikasi Android, sangat penting untuk menyimpan data secara lokal agar tetap tersedia meskipun aplikasi ditutup atau perangkat dimatikan. Salah satu pendekatan untuk menyimpan data lokal secara permanen adalah dengan menggunakan database. Android menyediakan berbagai pilihan untuk data persistence, dan salah satu solusi modern dan direkomendasikan adalah Room Database.

Pada bagian Data Persistence 2 ini, kita akan mempelajari bagaimana menyimpan data terstruktur menggunakan SQLite melalui pendekatan yang lebih efisien dan type-safe dengan bantuan Room, library resmi dari Android Jetpack.

__Room Database__ adalah library open-source Android yang membantu developer dalam membuat dan mengelola database SQLite dengan mudah dan efisien. Library ini dikembangkan oleh Google dan menjadi bagian dari Android Jetpack.

## Komponen Room Database

- __Database__ : Berisi pemegang _database_. Berfungsi sebagai titik akses utama untuk melakukan koneksi ke _database_ dari aplikasi Anda. Sebuah kelas yang akan diberi anotasi dengan `@Database` harus memenuhi ketentuan berikut:
    - Harus menjadi kelas abstrak yang diberi turunan kelas RoomDatabase.
    - Sertakan daftar entitas yang berkaitan dengan database dalam anotasi.
    - Berisi metode abstrak yang memiliki 0 argumen dan mengembalikan kelas @Dao.
- __Entity__ : Mempresentasikan tabel yang ada pada _database_ Anda.
- __DAO__ : Berisi metode yang digunakan untuk mengakses _database_.

Aplikasi menggunakan database Room untuk mendapatkan objek akses data atau DAO, yang terkait dengan _database_ itu. Aplikasi kemudian menggunakan setiap DAO untuk mendapatkan entitas dari _database_ dan menyimpan perubahan kembali ke _database_. Akhirnya, aplikasi menggunakan entitas untuk mendapatkan dan menetapkan nilai yang sesuai dengan kolom tabel dalam database.

## Hubungan antar Komponen Room

![Komponen Room](img/room.png)

Cuplikan kode berikut berisi contoh konfigurasi database dengan satu entitas dan satu DAO:

### Kelas User

```kotlin
@Entity
class User (
    @PrimaryKey
    var uid: Long,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String
)
```

Lihat kode di atas. Nama pada setiap _field_ memiliki _default_ sama dengan nama variabel dari _field_ tersebut. Contohnya variabel `uid`, nama field-nya uid. Kemudian, Anda bisa menentukan field mana yang menjadi primary key dengan memberi anotasi `@PrimaryKey` pada variable-nya.

### Kelas UserDao

```kotlin
@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    val getAll: List<User>
 
    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>
 
    @Query("SELECT * FROM user WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User
 
    @Insert
    fun insertAll(vararg users: User)
 
    @Delete
    fun delete(user: User)
}
```

Pada kelas DAO, Room sudah menyediakan anotasi _insert_ untuk menambahkan data, _delete_ untuk menghapus data dan query untuk melakukan _request_ pada database.

### Kelas UserDatabase

```kotlin
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
```

Setelah membuat berkas di atas, Anda mendapatkan _instance_ dari _database_ yang dibuat menggunakan kode berikut:

```kotlin
var db: AppDatabase = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database-name").build()
```

Jika dibandingkan dengan API SQlite, Room lebih ringkas dan lebih mudah dalam melakukan _maintenance_. Kita bisa mengubah sebuah kelas POJO menjadi entitas dengan menambahkan anotasi `@Entity` dan menentukan atribut dari tabel tersebut seperti _primary key_-nya apakah boleh null atau tidak. 

Untuk mempelajari Room Database lebih lanjut, kalian dapat mengunjungi link berikut:

[Room Database](https://developer.android.com/training/data-storage/room)

## Latihan

[Latihan Room Database](latihan.md)
