
# Dependency Injection dengan Hilt

Hilt adalah library dependency injection untuk Android yang mengurangi boilerplate saat melakukan dependency injection secara manual dalam proyek Anda. Melakukan dependency injection secara manual mengharuskan Anda untuk membuat setiap kelas dan dependensinya secara manual, serta menggunakan container untuk menggunakan kembali dan mengelola dependensi.

Hilt menyediakan cara standar untuk menggunakan DI dalam aplikasi Anda dengan menyediakan container untuk setiap kelas Android dalam proyek Anda dan mengelola siklus hidupnya secara otomatis. Hilt dibangun di atas library Dagger yang populer untuk mendapatkan manfaat dari pemeriksaan pada waktu kompilasi, kinerja pada waktu berjalan, skalabilitas, dan dukungan Android Studio yang disediakan oleh Dagger. Untuk informasi lebih lanjut, lihat [Hilt dan Dagger](https://developer.android.com/training/dependency-injection/hilt-cheatsheet).

---

## Menambahkan Dependensi

Pertama, tambahkan plugin `hilt-android-gradle-plugin` ke file `build.gradle` root proyek Anda:

<details>
<summary>Groovy</summary>

```groovy
plugins {
  ...
  id 'com.google.dagger.hilt.android' version '2.56.2' apply false
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
plugins {
  ...
  id("com.google.dagger.hilt.android") version "2.56.2" apply false
}
```
</details>

Kemudian, terapkan plugin Gradle dan tambahkan dependensi berikut di file `app/build.gradle` Anda:

<details>
<summary>Groovy</summary>

```groovy
plugins {
  id 'com.google.devtools.ksp'
  id 'com.google.dagger.hilt.android'
}

android {
  ...
}

dependencies {
  implementation "com.google.dagger:hilt-android:2.56.2"
  ksp "com.google.dagger:hilt-compiler:2.56.2"
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
plugins {
  id("com.google.devtools.ksp")
  id("com.google.dagger.hilt.android")
}

android {
  ...
}

dependencies {
  implementation("com.google.dagger:hilt-android:2.56.2")
  ksp("com.google.dagger:hilt-android-compiler:2.56.2")
}
```
</details>

> **Catatan:** Proyek yang menggunakan Hilt dan data binding memerlukan Android Studio 4.0 atau lebih baru.  
> Hilt menggunakan fitur Java 8. Untuk mengaktifkan Java 8 dalam proyek Anda, tambahkan yang berikut ke file `app/build.gradle`:

<details>
<summary>Groovy</summary>

```groovy
android {
  ...
  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
}
```
</details>

<details>
<summary>Kotlin</summary>

```kotlin
android {
  ...
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}
```
</details>

---

## Kelas Aplikasi Hilt

Semua aplikasi yang menggunakan Hilt harus memiliki kelas `Application` yang dianotasi dengan `@HiltAndroidApp`:

<details>
<summary>Kotlin</summary>

```kotlin
@HiltAndroidApp
class ExampleApplication : Application() { ... }
```
</details>

<details>
<summary>Java</summary>

```java
@HiltAndroidApp
public class ExampleApplication extends Application { ... }
```
</details>

Anotasi `@HiltAndroidApp` memicu pembuatan kode Hilt, termasuk kelas dasar untuk aplikasi Anda yang berfungsi sebagai container dependency pada level aplikasi.

---

## Menyuntikkan Dependensi ke Kelas Android

Setelah Hilt diatur di kelas `Application` Anda dan komponen pada level aplikasi tersedia, Hilt dapat menyediakan dependensi ke kelas Android lain yang diberi anotasi `@AndroidEntryPoint`:

<details>
<summary>Kotlin</summary>

```kotlin
@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() { ... }
```
</details>

<details>
<summary>Java</summary>

```java
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity { ... }
```
</details>

Hilt saat ini mendukung kelas-kelas Android berikut:

- Application (dengan menggunakan `@HiltAndroidApp`)
- ViewModel (dengan menggunakan `@HiltViewModel`)
- Activity
- Fragment
- View
- Service
- BroadcastReceiver

> **Catatan:** Jika Anda menganotasi sebuah kelas Android dengan `@AndroidEntryPoint`, Anda juga harus menganotasi kelas Android yang bergantung padanya. Misalnya, jika Anda menganotasi sebuah fragment, maka Anda harus menganotasi aktivitas di mana Anda menggunakan fragment tersebut.  
> Hilt hanya mendukung activity yang memperluas `ComponentActivity`, seperti `AppCompatActivity`.  
> Hilt hanya mendukung fragment yang memperluas `androidx.Fragment`.  
> Hilt tidak mendukung retained fragment.

Anotasi `@AndroidEntryPoint` menghasilkan komponen Hilt individu untuk setiap kelas Android dalam proyek Anda. Komponen tersebut dapat menerima dependensi dari kelas induknya sesuai dengan hierarki komponen.

Untuk mendapatkan dependensi dari sebuah komponen, gunakan anotasi `@Inject` untuk melakukan field injection:

<details>
<summary>Kotlin</summary>

```kotlin
@AndroidEntryPoint
class ExampleActivity : AppCompatActivity() {

  @Inject lateinit var analytics: AnalyticsAdapter
  ...
}
```
</details>

<details>
<summary>Java</summary>

```java
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity {

  @Inject AnalyticsAdapter analytics;
  ...
}
```
</details>

> **Catatan:** Field yang disuntikkan oleh Hilt tidak boleh bersifat private. Mencoba menyuntikkan field private akan menghasilkan kesalahan kompilasi.  
> Kelas yang disuntikkan oleh Hilt dapat memiliki kelas dasar lain yang juga menggunakan injection. Kelas-kelas tersebut tidak perlu anotasi `@AndroidEntryPoint` jika mereka bersifat abstrak.  
> Untuk mempelajari lebih lanjut tentang callback siklus hidup di mana kelas Android mendapatkan injection, lihat [Komponen Lifetimes](#Komponen-lifetimes).

---

## Mendefinisikan Binding Hilt

Untuk melakukan field injection, Hilt perlu mengetahui cara menyediakan instance dari dependensi yang diperlukan dari komponen yang sesuai. Binding berisi informasi yang diperlukan untuk menyediakan instance suatu tipe sebagai dependensi.

### Constructor Injection

Salah satu cara untuk menyediakan informasi binding ke Hilt adalah constructor injection. Gunakan anotasi `@Inject` pada konstruktor suatu kelas untuk memberi tahu Hilt cara menyediakan instance dari kelas tersebut:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
  private val service: AnalyticsService
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(AnalyticsService service) { ... }
}
```
</details>

Parameter dari konstruktor yang dianotasi adalah dependensi dari kelas tersebut. Dalam contoh ini, `AnalyticsAdapter` memiliki `AnalyticsService` sebagai dependensi. Oleh karena itu, Hilt juga harus mengetahui cara menyediakan instance `AnalyticsService`.

> **Catatan:** Saat build berlangsung, Hilt menghasilkan komponen Dagger untuk kelas Android. Kemudian, Dagger berjalan melalui kode Anda dan melakukan langkah-langkah berikut:  
> 1. Membangun dan memvalidasi grafik dependensi, memastikan tidak ada dependensi yang belum terpenuhi atau siklus dependensi.  
> 2. Menghasilkan kelas-kelas yang digunakan pada runtime untuk membuat objek dan dependensinya.

---

## Modul Hilt

Terkadang sebuah tipe tidak dapat di-`constructor-inject`. Ini dapat terjadi karena beberapa alasan. Misalnya, Anda tidak dapat melakukan constructor injection pada sebuah interface. Anda juga tidak dapat melakukan constructor injection pada klas yang tidak Anda miliki, seperti kelas dari library eksternal. Dalam kasus tersebut, Anda dapat menyediakan informasi binding ke Hilt dengan menggunakan modul Hilt.

Modul Hilt adalah kelas yang dianotasi dengan `@Module`. Seperti modul Dagger, modul ini memberi tahu Hilt cara menyediakan instance dari tipe tertentu. Berbeda dengan modul Dagger, Anda harus menganotasi modul Hilt dengan `@InstallIn` untuk memberi tahu Hilt pada kelas Android mana modul tersebut akan digunakan atau diinstal.

> **Catatan:** Modul Hilt berbeda dari modul Gradle.  
> Dependensi yang Anda sediakan dalam modul Hilt tersedia di semua komponen yang dihasilkan yang terkait dengan kelas Android tempat Anda menginstal modul Hilt.  
> Karena pembuatan kode Hilt memerlukan akses ke semua modul Gradle yang menggunakan Hilt, modul Gradle yang mengompilasi kelas `Application` Anda juga harus memiliki semua modul Hilt dan kelas yang di-constructor-inject dalam dependensi transitifnya.

---

### Menyuntikkan Instance Interface dengan @Binds

Pertimbangkan contoh `AnalyticsService`. Jika `AnalyticsService` adalah sebuah interface, maka Anda tidak dapat melakukan constructor injection pada interface. Sebagai gantinya, sediakan informasi binding ke Hilt dengan membuat fungsi abstrak yang dianotasi dengan `@Binds` di dalam modul Hilt.

Anotasi `@Binds` memberi tahu Hilt implementasi mana yang harus digunakan ketika Hilt perlu menyediakan instance dari sebuah interface.

Fungsi yang dianotasi memberikan informasi berikut ke Hilt:
- Tipe kembalian fungsi menunjukkan interface apa yang disediakan Hilt.
- Parameter fungsi menunjukkan implementasi mana yang harus diberikan.

<details>
<summary>Kotlin</summary>

```kotlin
interface AnalyticsService {
  fun analyticsMethods()
}

// Constructor-injectable, karena Hilt perlu mengetahui
// cara menyediakan instance AnalyticsServiceImpl.
class AnalyticsServiceImpl @Inject constructor(
  ...
) : AnalyticsService { ... }

@Module
@InstallIn(ActivityComponent::class)
abstract class AnalyticsModule {

  @Binds
  abstract fun bindAnalyticsService(
    analyticsServiceImpl: AnalyticsServiceImpl
  ): AnalyticsService
}
```
</details>

<details>
<summary>Java</summary>

```java
public interface AnalyticsService {
  void analyticsMethods();
}

// Constructor-injectable, karena Hilt perlu mengetahui
// cara menyediakan instance AnalyticsServiceImpl.
public class AnalyticsServiceImpl implements AnalyticsService {
  @Inject
  public AnalyticsServiceImpl(...) { ... }
}

@Module
@InstallIn(ActivityComponent.class)
public abstract class AnalyticsModule {
  @Binds
  public abstract AnalyticsService bindAnalyticsService(
    AnalyticsServiceImpl analyticsServiceImpl
  );
}
```
</details>

Modul Hilt `AnalyticsModule` dianotasi dengan `@InstallIn(ActivityComponent.class)` karena Anda ingin Hilt menyuntikkan dependensi tersebut ke `ExampleActivity`. Anotasi ini berarti semua dependensi dalam `AnalyticsModule` tersedia di semua aktivitas aplikasi.

---

### Menyuntikkan Instance dengan @Provides

Interface bukan satu-satunya kasus di mana Anda tidak dapat melakukan constructor injection pada sebuah tipe. Constructor injection juga tidak dimungkinkan jika Anda tidak memiliki kelas tersebut karena berasal dari library eksternal (misalnya Retrofit, OkHttpClient, atau Room), atau jika instance harus dibuat dengan pola builder.

Dalam kasus ini, Anda dapat memberi tahu Hilt cara menyediakan instance dari tipe tersebut dengan membuat fungsi di dalam modul Hilt dan menganotasinya dengan `@Provides`.

Fungsi yang dianotasi memberikan informasi berikut ke Hilt:
- Tipe kembalian fungsi menunjukkan tipe apa yang disediakan Hilt.
- Parameter fungsi menunjukkan dependensi tipe tersebut.
- Badan fungsi menunjukkan cara Hilt menyediakan instance tipe tersebut. Hilt mengeksekusi tubuh fungsi setiap kali perlu menyediakan instance.

<details>
<summary>Kotlin</summary>

```kotlin
@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {

  @Provides
  fun provideAnalyticsService(
    // Potensial dependensi tipe ini
  ): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService::class.java)
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
@Module
@InstallIn(ActivityComponent.class)
public class AnalyticsModule {
  @Provides
  public static AnalyticsService provideAnalyticsService(
    // Potensial dependensi tipe ini
  ) {
      return new Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService.class);
  }
}
```
</details>

---

## Menyediakan Beberapa Binding untuk Tipe yang Sama

Dalam kasus Anda membutuhkan Hilt menyediakan implementasi berbeda dari tipe yang sama sebagai dependensi, Anda harus memberikan beberapa binding. Anda dapat mendefinisikan beberapa binding untuk tipe yang sama menggunakan qualifier.

Qualifier adalah anotasi yang Anda gunakan untuk mengidentifikasi binding tertentu untuk sebuah tipe ketika tipe tersebut memiliki beberapa binding yang didefinisikan.

Pertama, definisikan qualifier yang akan digunakan untuk menganotasi metode `@Binds` atau `@Provides`:

<details>
<summary>Kotlin</summary>

```kotlin
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthInterceptorOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OtherInterceptorOkHttpClient
```
</details>

<details>
<summary>Java</summary>

```java
@Qualifier
@Retention(RetentionPolicy.BINARY)
public @interface AuthInterceptorOkHttpClient {}

@Qualifier
@Retention(RetentionPolicy.BINARY)
public @interface OtherInterceptorOkHttpClient {}
```
</details>

Kemudian, Hilt perlu mengetahui cara menyediakan instance tipe yang sesuai dengan setiap qualifier. Dalam contoh ini, Anda dapat menggunakan modul Hilt dengan `@Provides`. Kedua metode memiliki tipe kembalian yang sama, tetapi qualifier memberi label keduanya sebagai binding yang berbeda:

<details>
<summary>Kotlin</summary>

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @AuthInterceptorOkHttpClient
  @Provides
  fun provideAuthInterceptorOkHttpClient(
    authInterceptor: AuthInterceptor
  ): OkHttpClient {
      return OkHttpClient.Builder()
               .addInterceptor(authInterceptor)
               .build()
  }

  @OtherInterceptorOkHttpClient
  @Provides
  fun provideOtherInterceptorOkHttpClient(
    otherInterceptor: OtherInterceptor
  ): OkHttpClient {
      return OkHttpClient.Builder()
               .addInterceptor(otherInterceptor)
               .build()
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {

  @AuthInterceptorOkHttpClient
  @Provides
  public static OkHttpClient provideAuthInterceptorOkHttpClient(
    AuthInterceptor authInterceptor
  ) {
      return new OkHttpClient.Builder()
               .addInterceptor(authInterceptor)
               .build();
  }

  @OtherInterceptorOkHttpClient
  @Provides
  public static OkHttpClient provideOtherInterceptorOkHttpClient(
    OtherInterceptor otherInterceptor
  ) {
      return new OkHttpClient.Builder()
               .addInterceptor(otherInterceptor)
               .build();
  }
}
```
</details>

Anda dapat menyuntikkan tipe spesifik yang Anda butuhkan dengan menganotasi field atau parameter dengan qualifier yang sesuai:

<details>
<summary>Kotlin</summary>

```kotlin
// Sebagai dependensi kelas lain.
@Module
@InstallIn(ActivityComponent::class)
object AnalyticsModule {

  @Provides
  fun provideAnalyticsService(
    @AuthInterceptorOkHttpClient okHttpClient: OkHttpClient
  ): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .client(okHttpClient)
               .build()
               .create(AnalyticsService::class.java)
  }
}

// Sebagai dependensi kelas yang di-constructor-inject.
class ExampleServiceImpl @Inject constructor(
  @AuthInterceptorOkHttpClient private val okHttpClient: OkHttpClient
) : ...

// Saat field injection.
@AndroidEntryPoint
class ExampleActivity: AppCompatActivity() {

  @AuthInterceptorOkHttpClient
  @Inject lateinit var okHttpClient: OkHttpClient
}
```
</details>

<details>
<summary>Java</summary>

```java
// Sebagai dependensi kelas lain.
@Module
@InstallIn(ActivityComponent.class)
public class AnalyticsModule {

  @Provides
  public static AnalyticsService provideAnalyticsService(
    @AuthInterceptorOkHttpClient OkHttpClient okHttpClient
  ) {
      return new Retrofit.Builder()
               .baseUrl("https://example.com")
               .client(okHttpClient)
               .build()
               .create(AnalyticsService.class);
  }
}

// Sebagai dependensi kelas yang di-constructor-inject.
public class ExampleServiceImpl {
  @Inject
  public ExampleServiceImpl(
    @AuthInterceptorOkHttpClient OkHttpClient okHttpClient
  ) { ... }
}

// Saat field injection.
@AndroidEntryPoint
public class ExampleActivity extends AppCompatActivity {

  @AuthInterceptorOkHttpClient
  @Inject OkHttpClient okHttpClient;
}
```
</details>

> **Praktik terbaik:** Jika Anda menambahkan qualifier pada sebuah tipe, tambahkan qualifier pada semua kemungkinan cara untuk menyediakan dependensi tersebut. Membiarkan implementasi dasar tanpa qualifier berisiko menyebabkan Hilt menyuntikkan dependensi yang salah.

---

## Qualifier Bawaan di Hilt

Hilt menyediakan beberapa qualifier bawaan. Misalnya, ketika Anda mungkin membutuhkan kelas `Context` dari aplikasi atau activity, Hilt menyediakan qualifier `@ApplicationContext` dan `@ActivityContext`.

Misalkan kelas `AnalyticsAdapter` dari contoh membutuhkan konteks activity. Contoh kode berikut menunjukkan cara menyediakan konteks activity ke `AnalyticsAdapter`:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
    @ActivityContext private val context: Context,
    private val service: AnalyticsService
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(
    @ActivityContext Context context,
    AnalyticsService service
  ) { ... }
}
```
</details>

Untuk binding bawaan lainnya yang tersedia di Hilt, lihat [Binding Default Komponen](#Binding-default-komponen).

---

## Komponen yang Dihasilkan untuk Kelas Android

Untuk setiap kelas Android di mana Anda dapat melakukan field injection, ada komponen Hilt yang terkait yang dapat Anda gunakan dalam anotasi `@InstallIn`. Setiap komponen Hilt bertanggung jawab untuk menyuntikkan dependensi ke kelas Android yang sesuai.

Tabel berikut mencantumkan komponen Hilt dan kelas Android yang disuntiknya:

| Komponen Hilt                  | Menyuntik untuk                |
| ------------------------------ | ------------------------------ |
| `SingletonComponent`           | Application                    |
| `ActivityRetainedComponent`    | N/A                            |
| `ViewModelComponent`           | ViewModel                      |
| `ActivityComponent`            | Activity                       |
| `FragmentComponent`            | Fragment                       |
| `ViewComponent`                | View                           |
| `ViewWithFragmentComponent`    | View yang dianotasi `@WithFragmentBindings` |
| `ServiceComponent`             | Service                        |

> **Catatan:** Hilt tidak membuat komponen untuk broadcast receiver karena Hilt menyuntikkan broadcast receiver langsung dari `SingletonComponent`.

---

## Komponen Lifetimes

Hilt secara otomatis membuat dan menghancurkan instance komponen yang dihasilkan mengikuti siklus hidup kelas Android yang sesuai.

| Komponen yang Dihasilkan         | Dibuat pada                 | Dihancurkan pada            |
| -------------------------------- | --------------------------- | --------------------------- |
| `SingletonComponent`             | Application#onCreate()      | Aplikasi dihancurkan        |
| `ActivityRetainedComponent`      | Activity#onCreate()         | Activity#onDestroy()        |
| `ViewModelComponent`             | ViewModel dibuat            | ViewModel dihancurkan       |
| `ActivityComponent`              | Activity#onCreate()         | Activity#onDestroy()        |
| `FragmentComponent`              | Fragment#onAttach()         | Fragment#onDestroy()        |
| `ViewComponent`                  | View#super()                | View dihancurkan            |
| `ViewWithFragmentComponent`      | View#super()                | View dihancurkan            |
| `ServiceComponent`               | Service#onCreate()          | Service#onDestroy()         |

> **Catatan:** `ActivityRetainedComponent` tetap hidup saat perubahan konfigurasi, jadi dibuat pada `Activity#onCreate()` pertama dan dihancurkan pada `Activity#onDestroy()` terakhir.

---

## Scope Komponen

Secara default, semua binding di Hilt tidak memiliki scope. Ini berarti setiap kali aplikasi Anda meminta binding, Hilt membuat instance baru dari tipe yang diperlukan.

Namun, Hilt juga memungkinkan binding di-scope ke komponen tertentu. Hilt hanya membuat binding yang di-scope satu kali per instance komponen yang di-scope, dan semua permintaan untuk binding tersebut akan menggunakan instance yang sama.

Tabel berikut mencantumkan anotasi scope untuk setiap komponen yang dihasilkan:

| Kelas Android | Komponen yang Dihasilkan         | Scope                      |
| ------------- | --------------------------------- | -------------------------- |
| Application   | `SingletonComponent`              | `@Singleton`               |
| Activity      | `ActivityRetainedComponent`       | `@ActivityRetainedScoped`   |
| ViewModel     | `ViewModelComponent`              | `@ViewModelScoped`         |
| Activity      | `ActivityComponent`               | `@ActivityScoped`          |
| Fragment      | `FragmentComponent`               | `@FragmentScoped`          |
| View          | `ViewComponent`                   | `@ViewScoped`              |
| View yang dianotasi `@WithFragmentBindings` | `ViewWithFragmentComponent` | `@ViewScoped`       |
| Service       | `ServiceComponent`                | `@ServiceScoped`           |

> **Catatan:** Memberi scope pada binding ke komponen dapat memakan memori karena objek yang diberikan tetap hidup hingga komponen tersebut dihancurkan. Minimalkan penggunaan binding yang di-scope dalam aplikasi Anda. Penggunaan binding yang di-scope sesuai ketika binding memiliki state internal yang memerlukan instance yang sama dalam cakupan tertentu, untuk binding yang memerlukan sinkronisasi, atau untuk binding yang telah terbukti mahal untuk dibuat.

Contoh memberi scope `AnalyticsAdapter` ke `ActivityComponent`:

<details>
<summary>Kotlin</summary>

```kotlin
@ActivityScoped
class AnalyticsAdapter @Inject constructor(
  private val service: AnalyticsService
) { ... }
```
</details>

> **Catatan:** Jika Anda membutuhkan binding yang tersedia di seluruh aplikasi, beri scope-nya ke `SingletonComponent`. Misalnya:

<details>
<summary>Kotlin</summary>

```kotlin
// Jika AnalyticsService adalah sebuah interface.
@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {

  @Singleton
  @Binds
  abstract fun bindAnalyticsService(
    analyticsServiceImpl: AnalyticsServiceImpl
  ): AnalyticsService
}

// Jika Anda tidak memiliki kelas AnalyticsService.
@Module
@InstallIn(SingletonComponent::class)
object AnalyticsModule {

  @Singleton
  @Provides
  fun provideAnalyticsService(): AnalyticsService {
      return Retrofit.Builder()
               .baseUrl("https://example.com")
               .build()
               .create(AnalyticsService::class.java)
  }
}
```
</details>

---

## Hierarki Komponen

Menginstal modul ke dalam komponen memungkinkan binding-nya diakses sebagai dependensi dari binding lain dalam komponen tersebut atau dalam komponen anak di bawahnya dalam hierarki komponen:

```
ViewWithFragmentComponent berada di bawah FragmentComponent.
FragmentComponent dan ViewComponent berada di bawah ActivityComponent.
ActivityComponent berada di bawah ActivityRetainedComponent.
ViewModelComponent berada di bawah ActivityRetainedComponent.
ActivityRetainedComponent dan ServiceComponent berada di bawah SingletonComponent.
```

> **Catatan:** Secara default, jika Anda melakukan field injection dalam sebuah view, `ViewComponent` dapat menggunakan binding yang didefinisikan dalam `ActivityComponent`. Jika Anda juga perlu menggunakan binding yang didefinisikan dalam `FragmentComponent` dan view tersebut adalah bagian dari fragment, gunakan anotasi `@WithFragmentBindings` dengan `@AndroidEntryPoint`.

---

## Binding Default Komponen

Setiap komponen Hilt dilengkapi dengan sekumpulan binding default yang dapat disuntikkan Hilt sebagai dependensi ke binding kustom Anda. Perlu dicatat bahwa binding ini sesuai dengan jenis activity dan fragment umum, bukan subclass tertentu. Ini karena Hilt menggunakan satu definisi komponen activity untuk menyuntikkan semua aktivitas. Setiap aktivitas memiliki instance komponen yang berbeda.

| Komponen Android               | Binding Default                 |
| ------------------------------ | ------------------------------- |
| `SingletonComponent`           | Application                     |
| `ActivityRetainedComponent`    | Application                     |
| `ViewModelComponent`           | SavedStateHandle                |
| `ActivityComponent`            | Application, Activity           |
| `FragmentComponent`            | Application, Activity, Fragment |
| `ViewComponent`                | Application, Activity, View     |
| `ViewWithFragmentComponent`    | Application, Activity, Fragment, View |
| `ServiceComponent`             | Application, Service            |

Binding konteks aplikasi juga tersedia menggunakan `@ApplicationContext`. Misalnya:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsServiceImpl @Inject constructor(
  @ApplicationContext context: Context
) : AnalyticsService { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsServiceImpl {
  @Inject
  public AnalyticsServiceImpl(@ApplicationContext Context context) { ... }
}
```
</details>

Binding konteks activity juga tersedia menggunakan `@ActivityContext`. Misalnya:

<details>
<summary>Kotlin</summary>

```kotlin
class AnalyticsAdapter @Inject constructor(
  @ActivityContext context: Context
) { ... }
```
</details>

<details>
<summary>Java</summary>

```java
public class AnalyticsAdapter {
  @Inject
  public AnalyticsAdapter(@ActivityContext Context context) { ... }
}
```
</details>

---

## Menyuntikkan Dependensi di Kelas yang Tidak Didukung oleh Hilt

Hilt hadir dengan dukungan untuk kelas-kelas Android yang umum. Namun, Anda mungkin perlu melakukan field injection di kelas yang tidak didukung oleh Hilt.

Dalam kasus tersebut, Anda dapat membuat entry point menggunakan anotasi `@EntryPoint`. Entry point adalah batas antara kode yang dikelola oleh Hilt dan kode yang tidak. Entry point adalah titik di mana kode pertama kali masuk ke dalam grafik objek yang dikelola oleh Hilt. Entry point memungkinkan Hilt menggunakan kode yang tidak dikelola Hilt untuk menyediakan dependensi dalam grafik dependensi.

Sebagai contoh, Hilt tidak mendukung content provider secara langsung. Jika Anda ingin content provider menggunakan Hilt untuk mendapatkan beberapa dependensi, Anda perlu mendefinisikan interface yang dianotasi dengan `@EntryPoint` untuk setiap tipe binding yang Anda inginkan dan menyertakan qualifier. Kemudian tambahkan `@InstallIn` untuk menentukan komponen di mana entry point diinstal sebagai berikut:

<details>
<summary>Kotlin</summary>

```kotlin
class ExampleContentProvider : ContentProvider() {

  @EntryPoint
  @InstallIn(SingletonComponent::class)
  interface ExampleContentProviderEntryPoint {
    fun analyticsService(): AnalyticsService
  }

  ...
}
```
</details>

<details>
<summary>Java</summary>

```java
public class ExampleContentProvider extends ContentProvider {

  @EntryPoint
  @InstallIn(SingletonComponent.class)
  public interface ExampleContentProviderEntryPoint {
    AnalyticsService analyticsService();
  }

  ...
}
```
</details>

Untuk mengakses entry point, gunakan metode statis yang sesuai dari `EntryPointAccessors`. Parameter harus berupa instance komponen atau objek `@AndroidEntryPoint` yang bertindak sebagai penampung komponen. Pastikan bahwa komponen yang Anda berikan sebagai parameter dan metode statis `EntryPointAccessors` keduanya sesuai dengan kelas Android dalam anotasi `@InstallIn` pada interface `@EntryPoint`:

<details>
<summary>Kotlin</summary>

```kotlin
class ExampleContentProvider: ContentProvider() {
    ...

  override fun query(...): Cursor {
    val appContext = context?.applicationContext ?: throw IllegalStateException()
    val hiltEntryPoint =
      EntryPointAccessors.fromApplication(appContext, ExampleContentProviderEntryPoint::class.java)

    val analyticsService = hiltEntryPoint.analyticsService()
    ...
  }
}
```
</details>

<details>
<summary>Java</summary>

```java
public class ExampleContentProvider extends ContentProvider {
    ...

  @Override
  public Cursor query(...) {
    Context appContext = getContext().getApplicationContext();
    ExampleContentProviderEntryPoint hiltEntryPoint =
      EntryPointAccessors.fromApplication(appContext, ExampleContentProviderEntryPoint.class);

    AnalyticsService analyticsService = hiltEntryPoint.analyticsService();
    ...
  }
}
```
</details>

> **Catatan:** Dalam contoh ini, Anda harus menggunakan konteks aplikasi untuk mengambil entry point karena entry point diinstal pada `SingletonComponent`. Jika binding yang ingin Anda ambil berada di `ActivityComponent`, gunakan konteks activity.

---

## Hilt dan Dagger

Hilt dibangun di atas library dependency injection Dagger, memberikan cara standar untuk memasukkan Dagger ke dalam aplikasi Android.

Terkait Dagger, tujuan Hilt adalah sebagai berikut:
1. Menyederhanakan infrastruktur terkait Dagger untuk aplikasi Android.
2. Membuat satu set komponen dan scope standar untuk mempermudah setup, keterbacaan, dan berbagi kode antar aplikasi.
3. Menyediakan cara mudah untuk menyediakan binding berbeda ke berbagai tipe build, seperti testing, debug, atau release.

Karena sistem operasi Android membuat banyak kelas framework-nya sendiri, menggunakan Dagger di aplikasi Android mengharuskan Anda menulis banyak kode boilerplate. Hilt mengurangi kode boilerplate yang terlibat dalam menggunakan Dagger di aplikasi Android. Hilt secara otomatis menghasilkan dan menyediakan hal-hal berikut:

- Komponen untuk mengintegrasikan kelas framework Android dengan Dagger yang seharusnya Anda buat sendiri.
- Anotasi scope untuk digunakan dengan komponen yang dihasilkan Hilt secara otomatis.
- Binding bawaan untuk mewakili kelas Android seperti Application atau Activity.
- Qualifier bawaan untuk mewakili `@ApplicationContext` dan `@ActivityContext`.

