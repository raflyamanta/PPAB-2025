package com.example.week7c.commons

import kotlinx.serialization.Serializable

@Serializable
class Routes {
    @Serializable
    object Auth

    @Serializable
    data class Home(
        val name: String
    )
}