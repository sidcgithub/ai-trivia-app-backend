package com.trivigenai.service

import com.trivigenai.models.Round
import com.trivigenai.repository.GenAIRepository

class GenAIService(private val genAIRepository: GenAIRepository) {
    fun getTrivia(topic: String): Round = genAIRepository.generateTrivia(topic)
}