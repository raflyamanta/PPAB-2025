package com.example.week9d

import javax.inject.Inject

class ArticleRepository @Inject constructor(){
    fun getArticles(): List<Article> {
        return listOf(
            Article(1, "Jetpack Compose Basics", "Learn about Jetpack Compose..."),
            Article(2, "State Management", "Understanding State in Compose..."),
            Article(3, "Using ViewModel", "Best practices of ViewModel in Compose...")
        )
    }
}

