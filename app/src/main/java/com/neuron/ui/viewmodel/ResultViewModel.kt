package com.neuron.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.neuron.ui.model.ChallengeResults
import com.neuron.ui.state.ResultScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ResultViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ResultScreenState())
    val uiState: StateFlow<ResultScreenState> = _uiState

    fun setResults(challengeResults: ChallengeResults) {
        _uiState.value = _uiState.value.copy(
            totalTime = challengeResults.totalTime,
            results = challengeResults.results,
        )
    }
}
