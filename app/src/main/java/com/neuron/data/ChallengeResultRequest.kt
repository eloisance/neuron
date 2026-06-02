package com.neuron.data

import kotlinx.serialization.Serializable

@Serializable
data class ChallengeResultRequest(
    val challengeText: String,
    val time: Long,
)