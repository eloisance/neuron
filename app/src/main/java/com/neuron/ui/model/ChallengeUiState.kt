package com.neuron.ui.model

import androidx.compose.runtime.Immutable

@Immutable
data class ChallengeUiState(
    val challengeSolvedCount: Int = 0,
    val challengeText: String = "",
    val result: Int = -1,
    val resultOptions: List<Int> = emptyList()
)