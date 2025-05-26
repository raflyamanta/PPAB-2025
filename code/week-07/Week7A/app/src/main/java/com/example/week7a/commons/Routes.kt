package com.example.week7a.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object Home

    @Serializable
    object CreateTodo
}