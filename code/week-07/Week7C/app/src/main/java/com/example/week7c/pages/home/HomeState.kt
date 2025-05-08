package com.example.week7c.pages.home

import com.example.week7c.models.Article

data class HomeState(
    val detail: Detail = Detail.Initial
) {
    interface Detail {
        object Initial : Detail
        data class Success(
            val articles: List<Article>
        ) : Detail
    }
}
