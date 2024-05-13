package com.trivigenai

import com.trivigenai.di.testModule
import com.trivigenai.models.Round
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.junit.After
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {

    @BeforeTest
    fun setup() {
        startKoin {
            loadKoinModules(
                testModule
            )
        }
    }

    @Test
    fun testAll() = testApplication {
        client.get("/trivia/").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals(Round.Error(message = "Topic missing..."), Json.decodeFromString<Round.Error>(bodyAsText()))
        }
    }

    @Test
    fun testTriviaNoTopic() = testApplication {
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome!", bodyAsText())
        }
    }

    @Test
    fun testTriviaWithTopic() = testApplication {
        client.get("/trivia/?topic='Fake'").apply {
            assertEquals(HttpStatusCode.OK, status)
            assert(bodyAsText().contains("Fake"))
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
