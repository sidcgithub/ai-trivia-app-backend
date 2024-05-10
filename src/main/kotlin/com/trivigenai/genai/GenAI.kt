package com.trivigenai.genai

import com.trivigenai.models.Round
import dev.langchain4j.model.chat.ChatLanguageModel
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel
import kotlinx.serialization.json.Json

object GenAI {

    val model: ChatLanguageModel

    init {
        model = chatLanguageModel()
    }

    private fun chatLanguageModel(): ChatLanguageModel {
        val model: ChatLanguageModel = VertexAiGeminiChatModel.builder()
            .project(System.getenv("PROJECT_ID"))
            .location(System.getenv("LOCATION"))
            .modelName("gemini-1.0-pro")
            .build()
        return model
    }

    fun generateTrivia(
        model: ChatLanguageModel,
        topicCategories: List<String>,
        topic: String?,
        retries: Int = 3
    ): Round {
        val prompt = buildPrompt(topicCategories, topic)
        var lastException: Exception? = null

        repeat(retries) {
            try {
                val triviaRound = Json.decodeFromString<Round.TriviaRound>(model.generate(prompt))
                triviaRound.verifyCategory(topicCategories)
                return triviaRound
            } catch (e: Exception) {
                lastException = e
                println("Error generating trivia: ${e.message}, retrying... (${retries - it - 1} retries left)")
            }
        }
        println(lastException?.message)
        return Round.Error(message = "Something went wrong..")
    }

    private fun Round.TriviaRound.verifyCategory(topicCategories: List<String>) {
        if (!topicCategories.contains(category)) {
            println("Trivia topic not found: retrying...")
            throw Exception()
        }
    }


    private fun buildPrompt(topicCategories: List<String>, topic: String?): String {
        return "Given a user provided topic, give 5 trivia questions with 4 options each and answer key " +
                "for each in strict JSON format with no other text or characters including ``` surrounding " +
                "it and also classify the topic into one of these categories ${
                    topicCategories.joinToString(",")
                } (Do not create any other categories than the ones given). Here is an example if the user provided " +
                "topic was 'Space and matter':\n" +
                "{ \"category\": \"Science\", \"questions\": [ { \"question\": \"What is the chemical symbol " +
                "for the element oxygen?\", \"options\": [\"O\", \"Ox\", \"Om\", \"Op\"], \"answer\": " +
                "0 }, { \"question\": \"Which planet is known as the Red Planet?\", \"options\": " +
                "[\"Earth\", \"Mars\", \"Jupiter\", \"Venus\"], \"answer\": 1 }, " +
                "// Additional questions... ] }\n" +
                "\n1) When classifying into a category if you can't match with the ones given, use " +
                "Miscellaneous but never ever choose something outside the provided list -> ${
                    topicCategories.joinToString(",")
                } \n" +
                "2) Only respond in JSON with no surrounding special characters or text. It should\n" +
                "3) Only one option out of the 4 options for each question should be valid. Provide the correct answer index as an integer (0-based index).\n" +
                "4) The JSON should start with '{' and end with '}' like a normal JSON object.\n" +
                "User defined topic: $topic"
    }
}

object CategoryHelper {
    val topicCategories = listOf(
        "History",
        "Geography",
        "Science",
        "Mathematics",
        "Philosophy",
        "Art",
        "Sports",
        "Literature",
        "Technology",
        "Economics",
        "Politics",
        "Culture",
        "Miscellaneous"
    )
}