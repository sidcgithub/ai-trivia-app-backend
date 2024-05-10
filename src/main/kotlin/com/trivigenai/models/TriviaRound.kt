package com.trivigenai.models

import kotlinx.serialization.Serializable

@Serializable
data class TriviaQuestion(
    val question: String,
    val options: List<String>,
    val answer: Int
)

@Serializable
sealed class Round {
    data class TriviaRound(
        val category: String,
        val questions: List<TriviaQuestion>
    ): Round()
    
    data class Error(
        val message: String
    ): Round()
}