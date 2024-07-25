package com.trivigenai

import com.trivigenai.plugins.configureDI
import com.trivigenai.plugins.configureRouting
import com.trivigenai.plugins.configureSerialization
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main(args: Array<String>) {
    embeddedServer(Netty, port = 8080) {
        configureRouting()
    }.start(true)
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("Unused")
fun Application.module() {
    configureDI()
    configureSerialization()
}

