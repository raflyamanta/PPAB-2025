package com.example.week7a.pages.authentication

data class AuthState(
    val detail: Detail = Detail.Initial
) {
    interface Detail {
        object Initial : Detail
        object Loading : Detail
        object Success : Detail
        data class Failure(
            val message: String
        ) : Detail
    }
}