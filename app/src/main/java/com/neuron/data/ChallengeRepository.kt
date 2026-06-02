package com.neuron.data

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ChallengeRepository(
    private val httpClient: HttpClient,
) {

    suspend fun sendChallengeResult(request: ChallengeResultRequest) {
        try {
            httpClient.post("/challenge-result") {
                contentType(ContentType.Application.Json)
                setBody(request)
            }
            println("Result sent successfully!")
        } catch (e: Exception) {
            println("Error sending result: ${e.message}")
            e.printStackTrace()
        }
    }
}