package com.trivigenai.repository

import com.trivigenai.models.Round
import kotlinx.serialization.json.Json

class FakeGenAIRepositoryImpl : GenAIRepository {
    override fun generateTrivia(topic: String?, retries: Int): Round {
        val jsonInput = """
    {
        "category": "Fake",
        "questions": [
            {
                "question": "Which city is known as the Eternal City?",
                "options": ["Rome", "Paris", "London", "Tokyo"],
                "answer": 0
            },
            {
                "question": "What is the tallest mountain in the world?",
                "options": ["Mount Everest", "K2", "Kangchenjunga", "Lhotse"],
                "answer": 0
            },
            {
                "question": "Which country is home to the Great Wall of China?",
                "options": ["China", "Mongolia", "North Korea", "Russia"],
                "answer": 0
            },
            {
                "question": "What is the longest river in the world?",
                "options": ["Nile", "Amazon", "Yangtze", "Mississippi"],
                "answer": 0
            },
            {
                "question": "Which city is known as the Windy City?",
                "options": ["Chicago", "New York City", "Los Angeles", "San Francisco"],
                "answer": 0
            }
        ]
    }
    """
        return Json.decodeFromString<Round.TriviaRound>(jsonInput)
    }
}