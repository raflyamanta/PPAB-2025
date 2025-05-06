package com.example.week7a.data

import kotlinx.coroutines.delay

class AuthRepository {
    suspend fun login(
        username: String,
        password: String
    ): Boolean {
        delay(2000)
        if (username == "rizalanggoro" && password == "1234")
            return true
        else
            throw Exception("auth incorrect!")
    }
}