## **Latihan: HTTP Request & Retrofit dalam Pengembangan Aplikasi Mobile**

Hari ini kita akan mencoba membuat aplikasi sederhana untuk menerapkan HTTP Request terhadap sebuah public API yang dapat dilihat dari https://dog.ceo/dog-api/documentation/.

Tambahkan dependensi pada `build.gradle.kts` pada level module app.

```
plugins {
    ....
    alias(libs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    ....
    kapt {
        correctErrorTypes = true
    }
}
dependencies {
    ....
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.google.dagger:hilt-android:2.56.2")
    kapt("com.google.dagger:hilt-compiler:2.56.2")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation ("androidx.navigation:navigation-compose:2.9.0")
}
```

Tambahkan juga di bagian `libs.version.toml`

```
[plugins]
....
hilt = { id = "com.google.dagger.hilt.android", version = "2.56.2" }
```

Tambahkan dependensi pada `build.gradle.kts` pada level project

```
plugins{
    ....
    alias(libs.plugins.hilt) apply false
}
```

Melakukan HTTP Request memerlukan akses ke internet. Maka dari itu, kita perlu menambahkan permission pada `AndroidManifest.xml`.

```
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">
    <uses-permission android:name="android.permission.INTERNET" />
    ....
</manifest>
```

Setelah selesai melakukan konfiguransi. Saatnya kita memulai aplikasi secara nyata. Struktur file yang akan diterapkan akan seperti berikut ini.

```
com.example.namaAplikasi
│
├── data
│   ├── remote
│   │   ├── ApiService.kt
│   │   ├── DogListResponse.kt
│   │   └── DogImageResponse.kt
│   └── repository
│       └── DogRepository.kt
│
├── ui
│   ├── DogImageScreen.kt
│   ├── DogListScreen.kt
│   └── DogViewModel.kt
│
├── MainActivity.kt
│
├── MyApp.kt
│
└── di
    └── AppModule.kt
```

AppModule.kt

```
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDogRepository(apiService: ApiService): DogRepository {
        return DogRepository(apiService)
    }
}

```

`DogImageResponse.kt`

```

data class DogImageResponse(
val message: String,
val status: String,
)
```

`DogListResponse.kt`

```

data class DogListResponse(
val message: Map<String, List<String>>,
val status: String
)
```

`DogRepository.kt`

```

class DogRepository @Inject constructor(
private val apiService: ApiService
) {
suspend fun fetchRandomDogImage(): DogImageResponse {
return apiService.getRandomDogImage()
}

    suspend fun fetchListBreeds(): DogListResponse {
        return apiService.getListBreeds()
    }

}
```

`ApiService.kt`

```

interface ApiService {
@GET("breeds/image/random")
suspend fun getRandomDogImage(): DogImageResponse

@GET("breeds/list/all")
    suspend fun getListBreeds(): DogListResponse

}
```

`DogViewModel.kt`

```
@HiltViewModel
class DogViewModel @Inject constructor(
    private val repository: DogRepository
) : ViewModel() {

    private val _dogImageUrl = MutableStateFlow<String?>(null)
    val dogImageUrl: StateFlow<String?> = _dogImageUrl

    private val _breedList = MutableStateFlow<Map<String, List<String>>?>(null)
    val breedList: StateFlow<Map<String, List<String>>?> = _breedList


    fun loadRandomDogImage() {
        viewModelScope.launch {
            try {
                val response = repository.fetchRandomDogImage()
                _dogImageUrl.value = response.message
            } catch (e: Exception) {
                _dogImageUrl.value = null
            }
        }
    }

    fun loadListBreeds() {
        viewModelScope.launch {
            try {
                val response = repository.fetchListBreeds()
                _breedList.value = response.message
            } catch (e: Exception) {
                _breedList.value = null
            }
        }
    }
}
```

`DogImageScreen.kt`

```
@Composable
fun DogImageScreen(viewModel: DogViewModel) {
    val dogImageUrl by viewModel.dogImageUrl.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        dogImageUrl?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Random Dog",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        } ?: Text("Loading...")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { viewModel.loadRandomDogImage() }) {
            Text("Load Another Dog")
        }
    }
}
```

`DogListScreen.kt`

```
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogListScreen(viewModel: DogViewModel) {
    val breedMap = viewModel.breedList.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadListBreeds()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("List of Dog Breeds") })
        }
    ) { paddingValues ->
        if (breedMap == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                breedMap!!.forEach { (breed, subBreeds) ->
                    item {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = breed.replaceFirstChar { it.uppercase() }, style = typography.titleMedium)

                            if (subBreeds.isNotEmpty()) {
                                subBreeds.forEach { sub ->
                                    Text(
                                        text = "↳ $sub",
                                        style = typography.bodySmall,
                                        modifier = Modifier.padding(start = 8.dp)
                                    )
                                }
                            }
                        }
                        Divider()
                    }
                }
            }
        }
    }
}

```

`MainActivity.kt`

```
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: DogViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cobaweek10Theme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(navController)
                    }
                    composable("randomImage") {
                        DogImageScreen(viewModel = viewModel)
                    }
                    composable("listBreeds") {
                        DogListScreen(viewModel = viewModel)
                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Home") })
        }
    ) { padding ->
        Column(modifier = Modifier
            .padding(padding)
            .padding(16.dp)) {
            Button(onClick = { navController.navigate("randomImage") }) {
                Text("Lihat Gambar Anjing Acak")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { navController.navigate("listBreeds") }) {
                Text("Lihat Daftar Ras Anjing")
            }
        }
    }
}

```

`MyApp.kt`

```
@HiltAndroidApp
class MyApp: Application() {
}
```

Daftarkan aplikasi ke dalam `AndroidManifest.xml`

```
<application
....
 android:name=".MyApp"
></application>
```

Jalankan aplikasi dan tara aplikasinya dah jadi!
[Tonton Video](./images/coba.mp4)
