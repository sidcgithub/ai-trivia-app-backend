package com.trivigenai.plugins

import com.trivigenai.service.GenAIService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText { "Hello World!" }
        }
        getTriviaRound()
    }
}

private fun Routing.getTriviaRound() {
    val service by inject<GenAIService>()
    get("/trivia/") {
        call.request.queryParameters["topic"]?.let {
            call.respond(HttpStatusCode.OK, service.getTrivia(it))
        } ?: call.respondText { "No topic chosen!" }

    }
}

