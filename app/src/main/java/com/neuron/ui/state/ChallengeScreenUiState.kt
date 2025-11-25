package com.neuron.ui.state

import androidx.compose.runtime.Immutable
import com.neuron.ui.model.ChallengeResults

@Immutable
data class ChallengeScreenUiState(
    val challengeSolvedCount: Int = 0,
    val elapsedTimeText: String = "",
    val challengeText: String = "",
    val result: Int = -1,
    val resultOptions: List<Int> = emptyList(),
    val challengeResults: ChallengeResults = ChallengeResults(),
    val isChallengeEnded: Boolean = false,
)