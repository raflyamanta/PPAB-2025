package com.example.week7a.commons

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable
    object Authentication

    @Serializable
    object Home
}