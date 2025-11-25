package com.neuron.ui.model

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Dashboard : Route

    @Serializable
    data object Challenge : Route

    @Serializable
    data class Result(val challengeResults: ChallengeResults) : Route
}

