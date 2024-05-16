package com.trivigenai

import com.trivigenai.di.testModule
import com.trivigenai.models.Round
import com.trivigenai.plugins.configureRouting
import com.trivigenai.plugins.configureSerialization
import com.trivigenai.plugins.configureTestDI
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import io.ktor.test.dispatcher.*
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Rule
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest : KoinTest {

    private val json = Json {
        ignoreUnknownKeys = true
    }

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private lateinit var testApp: TestApplication

    @BeforeTest
    fun setup() {
        testApp = TestApplication {
            application {
                configureTestDI()
                configureRouting()
                configureSerialization()
            }
            environment {
                config = MapApplicationConfig("ktor.environment" to "test")
            }
        }
    }

    @Test
    fun testRoot() = testSuspend {
        testApp.client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals("Welcome!", bodyAsText())
        }
    }

    @Test
    fun testTriviaNoTopic() = testSuspend {
        testApp.client.get("/trivia/").apply {
            assertEquals(HttpStatusCode.BadRequest, status)
            assertEquals(Round.Error(message = "Topic missing..."), json.decodeFromString<Round.Error>(bodyAsText()))
        }
    }

    @Test
    fun testTriviaWithTopic() = testSuspend {
        testApp.client.get("/trivia/?topic='Test'").apply {
            assertEquals(HttpStatusCode.OK, status)
            assertEquals(
                "Fake", json.decodeFromString<Round.TriviaRound>(bodyAsText()).category
            )
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}
