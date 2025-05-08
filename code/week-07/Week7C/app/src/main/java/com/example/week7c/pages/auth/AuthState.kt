package com.example.week7c.pages.auth

data class AuthState(
    val isLogin: Boolean = true,
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
