package com.example.week7a.domain.models

data class Todo(
    val id: String,
    val title: String,
    val description: String?,
    val status: Boolean
)
