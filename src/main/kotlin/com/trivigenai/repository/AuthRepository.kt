package com.trivigenai.repository

import io.github.jan.supabase.gotrue.user.UserInfo

interface AuthRepository {
    suspend fun signUp(email: String?, password: String?): UserInfo?
}