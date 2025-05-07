package com.example.week7c.data

import kotlinx.coroutines.delay

class AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ): Boolean {
        delay(2000)
        if (username == "hello" && password == "1234")
            return true
        else
            throw Exception("auth incorrect!")
    }
}