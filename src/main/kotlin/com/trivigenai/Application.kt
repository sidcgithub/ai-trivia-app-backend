package com.trivigenai

import com.trivigenai.plugins.configureDI
import com.trivigenai.plugins.configureRouting
import com.trivigenai.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("Unused")
fun Application.module() {
    configureDI()
    configureRouting()
    configureSerialization()
}

