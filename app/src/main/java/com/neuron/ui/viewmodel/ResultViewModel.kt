package com.neuron.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ResultScreenState(
    val totalTime: String = "",
    val calculationTimes: List<String> = emptyList()
)

class ResultViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ResultScreenState())
    val uiState: StateFlow<ResultScreenState> = _uiState

    init {
        // Mock data for now
        _uiState.value = ResultScreenState(
            totalTime = "01:23",
            calculationTimes = listOf("00:10", "00:15", "00:08", "00:50")
        )
    }
}
