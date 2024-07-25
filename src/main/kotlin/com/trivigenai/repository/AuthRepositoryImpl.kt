package com.trivigenai.repository

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.user.UserInfo

class AuthRepositoryImpl(): AuthRepository {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://oxgvdbfbepsdbsmzqeki.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im94Z3ZkYmZiZXBzZGJzbXpxZWtpIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjEzMzIzMTYsImV4cCI6MjAzNjkwODMxNn0.j7EwqHK_-V8-umoMZMERFqRIGtNIYy7X2bQn-Xswnog",
    ) {
        install(Auth)
    }

    override suspend fun signUp(
        email: String?,
        password: String?
    ): UserInfo? {
        val user = supabase.auth.signUpWith(Email) {
            this.email = email ?: "kazoroo1903@gmail.com"
            this.password = password ?: "fakepass"
        }

        return user
    }
}