package com.trivigenai.service

import com.trivigenai.repository.AuthRepository
import io.github.jan.supabase.gotrue.user.UserInfo

class AuthService(private val authRepository: AuthRepository) {
    suspend fun signUp(email: String?, password: String?): UserInfo? = authRepository.signUp(email, password)
}