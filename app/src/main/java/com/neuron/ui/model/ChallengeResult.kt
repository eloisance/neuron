package com.neuron.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChallengeResult(
    val challenge: String,
    val time: String,
)