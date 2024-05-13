package com.trivigenai.repository

import com.trivigenai.models.Round

interface GenAIRepository {
    fun generateTrivia(
        topic: String?
    ): Round
}