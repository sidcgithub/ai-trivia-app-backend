package com.trivigenai.plugins

import com.trivigenai.models.Round
import com.trivigenai.repository.AuthRepositoryImpl
import com.trivigenai.service.GenAIService
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
        install(ContentNegotiation) {
            json()
        }

        routing {
            get("/") {
                call.respondText { "Welcome!" }
            }
            getTriviaRound()
            signUpUser()
        }
    }

private fun Routing.getTriviaRound() {
    val service by inject<GenAIService>()
    get("/trivia/") {
        call.request.queryParameters["topic"]?.let {
            call.respond(HttpStatusCode.OK, service.getTrivia(it))
        } ?: call.respond(HttpStatusCode.BadRequest, Round.Error(message = "Topic missing..."))
    }
}

private fun Routing.signUpUser() {
    route("/auth") {
        get("/signup") {
            val email = call.request.queryParameters["email"]
            val password = call.request.queryParameters["password"]

            if (email != null && password != null) {
                val user = AuthRepositoryImpl().signUp(email, password)
                call.respond(HttpStatusCode.OK, "$email and $password \n and there is also user: ${user?.id}")
            } else {
                call.respond(HttpStatusCode.BadRequest, "Email or password missing")
            }
        }
    }
}

