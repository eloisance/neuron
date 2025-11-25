package com.neuron.ui.state

import androidx.compose.runtime.Immutable
import com.neuron.ui.model.ChallengeResult

@Immutable
data class ResultScreenState(
    val totalTime: String = "",
    val results: List<ChallengeResult> = emptyList(),
)