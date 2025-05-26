package com.example.week7c.domain.usecases

import com.example.week7c.data.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun call(
        username: String,
        password: String,
    ) = authRepository.login(
        username,
        password
    )
}