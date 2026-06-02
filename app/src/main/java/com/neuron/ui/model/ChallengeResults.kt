package com.neuron.ui.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChallengeResults(
    val totalTime: String = "",
    val results: List<ChallengeResult> = emptyList(),
)