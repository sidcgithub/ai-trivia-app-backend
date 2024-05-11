package com.trivigenai.plugins

import com.trivigenai.genai.GenAI.generateTrivia
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText { "Hello World!" }
        }
        getTriviaRound()
    }
}

private fun Routing.getTriviaRound() {
    get("/trivia/") {
        val topic = call.request.queryParameters["topic"]
        call.respond(HttpStatusCode.OK, generateTrivia(topic))
    }
}

