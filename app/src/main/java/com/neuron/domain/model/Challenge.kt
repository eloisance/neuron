package com.neuron.domain.model

data class Challenge(
    val challengeText: String,
    val result: Int,
    val resultOptions: List<Int>,
)