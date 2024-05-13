package com.trivigenai.plugins

import com.trivigenai.di.testModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureTestDI() {
    install(Koin) {
        modules(testModule)
    }
}