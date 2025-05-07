package com.example.week7c.data

data class Article(
    val id: Int,
    val title: String,
    val content: String
)

class HomeRepository {
    fun getArticles(): List<Article> {
        return listOf(
            Article(1, "Jetpack Compose Basics", "Learn about Jetpack Compose..."),
            Article(2, "State Management", "Understanding State in Compose..."),
            Article(3, "Using ViewModel", "Best practices of ViewModel in Compose..."),
            Article(4, "Navigation in Compose", "Navigating between screens in Compose..."),
            Article(5, "UI Testing with Compose", "Testing your Compose UI..."),
            Article(6, "Performance Tips", "Optimizing your Compose app..."),
            Article(7, "Advanced Layouts", "Creating complex layouts with Compose..."),
            Article(8, "Animations in Compose", "Adding animations to your Compose app..."),
            Article(9, "Integration with Legacy Views", "Using Compose with traditional Android views..."),
            Article(10, "Material Design in Compose", "Implementing Material Design in Compose..."),
        )
    }
}