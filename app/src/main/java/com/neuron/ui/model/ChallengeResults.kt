package com.neuron.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChallengeResults(
    val totalTime: String = "",
    val results: List<ChallengeResult> = emptyList(),
)