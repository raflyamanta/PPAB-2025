# ViewModel Pada Jetpack Compose


ViewModel adalah bagian dari **Architecture Component** di Android yang bertugas untuk:

* Mengambil data dari data layer (misal: Repository).
* Menyiapkan data untuk ditampilkan di layar (UI).

ViewModel akan **mempertahankan state** walau terjadi configuration change seperti **rotasi layar**.

---

## Hal yang Perlu Diperhatikan

* **ViewModel punya lifecycle lebih panjang** dari Composable ➔ Hindari menyimpan state seperti `ScaffoldState` atau `SnackbarHostState` di ViewModel (bisa menyebabkan memory leak).
* Gunakan ViewModel **hanya di screen level** (bukan di component kecil).
* Kirim **data** ke composable child, jangan kirim **ViewModel object** langsung.
* Pisahkan antara **Stateful Composable** (yang gunakan ViewModel) dan **Stateless Composable** (hanya menerima data).

---

## Contoh Implementasi ViewModel

Tambahkan dependencies di dalam files build.gradle.kts bagian module app terlebih dahulu, kemudian jangan lupa di sinkronkan.
```
dependencies {
    ...
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
}
```

### 1. Model Data

```kotlin
data class Article(
    val id: Int,
    val title: String,
    val content: String
)
```

### 2. Repository

```kotlin
class ArticleRepository {
    fun getArticles(): List<Article> {
        return listOf(
            Article(1, "Jetpack Compose Basics", "Learn about Jetpack Compose..."),
            Article(2, "State Management", "Understanding State in Compose..."),
            Article(3, "Using ViewModel", "Best practices of ViewModel in Compose...")
        )
    }
}
```

### 3. ViewModel

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: ArticleRepository = ArticleRepository()
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    init {
        loadArticles()
    }

    private fun loadArticles() {
        viewModelScope.launch {
            val data = repository.getArticles()
            _articles.value = data
        }
    }
}
```

### 4. Screen Composable

```kotlin
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ArticleScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = viewModel()
) {
    val articles by viewModel.articles.collectAsState()

    Box(modifier = modifier){
        ArticleList(articles = articles)
    }
}
```

### 5. Stateless Composable (List Layout)

```kotlin
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ArticleList(articles: List<Article>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(articles) { article ->
            ArticleItem(article = article)
        }
    }
}

@Composable
fun ArticleItem(article: Article) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp
                )
            )
        }
    }
}
```

Berikut Hasilnya: 

<img src="/week-07/img/v1.png" alt="v1" width="300"/>

---

## Best Practice ViewModel + Compose

* Gunakan **StateFlow** atau **LiveData** untuk expose state.
* Gunakan `viewModel()` untuk ambil ViewModel di Composable.
* Jangan simpan Composition-dependent state (misal: `SnackbarHostState`, `ScrollState`) di ViewModel.
* Kirim **data** saja ke stateless composable child.
