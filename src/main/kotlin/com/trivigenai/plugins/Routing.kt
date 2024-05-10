package com.trivigenai.plugins


import com.trivigenai.genai.CategoryHelper.topicCategories
import com.trivigenai.genai.GenAI.generateTrivia
import com.trivigenai.genai.GenAI.model
import dev.langchain4j.model.chat.ChatLanguageModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        val model: ChatLanguageModel = model
        get("/") {
            call.respondText { "Hello World!" }
        }
        getTriviaRound(model, topicCategories)
    }
}

private fun Routing.getTriviaRound(
    model: ChatLanguageModel,
    topicCategories: List<String>
) {
    get("/trivia/") {
        val topic = call.request.queryParameters["topic"]
        call.respond(HttpStatusCode.OK, generateTrivia(model, topicCategories, topic))
    }
}

