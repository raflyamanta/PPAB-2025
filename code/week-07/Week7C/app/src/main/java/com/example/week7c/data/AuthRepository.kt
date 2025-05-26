package com.example.week7c.data

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor() {
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