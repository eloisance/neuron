package com.neuron.ui.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Immutable
@Serializable
data class ChallengeResult(
    val challenge: String,
    val time: String,
)