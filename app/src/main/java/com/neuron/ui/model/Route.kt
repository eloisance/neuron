package com.neuron.ui.model

sealed interface Route

data object DashboardRoute : Route
data object ChallengeRoute : Route
data object ResultRoute : Route