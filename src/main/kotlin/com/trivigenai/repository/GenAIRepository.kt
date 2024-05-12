package com.trivigenai.repository

import com.trivigenai.models.Round

interface GenAIRepository {
    fun generateTrivia(
        topic: String?,
        retries: Int = 3
    ): Round
}