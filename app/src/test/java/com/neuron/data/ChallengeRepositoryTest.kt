package com.neuron.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ChallengeRepositoryTest {

    private fun buildClient(engine: MockEngine): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) { json() }
    }

    @Test
    fun `sendChallengeResult posts to the correct endpoint`() = runTest {
        val engine = MockEngine {
            respond("", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val repository = ChallengeRepository(buildClient(engine))

        repository.sendChallengeResult(ChallengeResultRequest("2 + 2", 1500L))

        val recorded = engine.requestHistory.single()
        assertEquals("/challenge-result", recorded.url.encodedPath)
        assertEquals(HttpMethod.Post, recorded.method)
    }

    @Test
    fun `sendChallengeResult sends JSON content type header`() = runTest {
        val engine = MockEngine {
            respond("", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val repository = ChallengeRepository(buildClient(engine))

        repository.sendChallengeResult(ChallengeResultRequest("5 * 3", 800L))

        val contentType = engine.requestHistory.single().body.contentType
        assertEquals(ContentType.Application.Json, contentType?.withoutParameters())
    }

    @Test
    fun `sendChallengeResult does not throw when network fails`() = runTest {
        val engine = MockEngine { throw RuntimeException("simulated network failure") }
        val repository = ChallengeRepository(buildClient(engine))

        // exception must be swallowed, not rethrown
        repository.sendChallengeResult(ChallengeResultRequest("1 + 1", 500L))
    }

    @Test
    fun `sendChallengeResult does not throw on server error response`() = runTest {
        val engine = MockEngine {
            respond("Internal Server Error", HttpStatusCode.InternalServerError)
        }
        val repository = ChallengeRepository(buildClient(engine))

        repository.sendChallengeResult(ChallengeResultRequest("10 / 2", 1200L))
    }

    @Test
    fun `sendChallengeResult sends exactly one request per call`() = runTest {
        val engine = MockEngine {
            respond("", HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString()))
        }
        val repository = ChallengeRepository(buildClient(engine))

        repository.sendChallengeResult(ChallengeResultRequest("7 - 3", 950L))

        assertEquals(1, engine.requestHistory.size)
    }
}
