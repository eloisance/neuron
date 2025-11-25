package com.neuron.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neuron.domain.ChallengeTimer
import com.neuron.domain.model.Challenge
import com.neuron.domain.usecase.GetChallengeUseCase
import com.neuron.ui.model.ChallengeResult
import com.neuron.ui.model.ChallengeResults
import com.neuron.ui.state.ChallengeScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChallengeViewModel(
    private val getChallengeUseCase: GetChallengeUseCase,
    private val challengeTimer: ChallengeTimer,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengeScreenUiState())
    val uiState: StateFlow<ChallengeScreenUiState> = _uiState.asStateFlow()

    private val currentResults: MutableList<ChallengeResult> = mutableListOf()

    init {
        viewModelScope.launch {
            challengeTimer.elapsedTimeMillis.collect { elapsedTime ->
                _uiState.update {
                    it.copy(
                        elapsedTimeText = formatMillisToMSS(millis = elapsedTime),
                    )
                }
            }
        }
        startChallenge()
    }

    fun answer(optionChosen: Int) {
        if (optionChosen == _uiState.value.result) {
            currentResults.add(
                ChallengeResult(
                    time = formatMillisToMSS(millis = challengeTimer.getAnswerTime()),
                    challenge = _uiState.value.challengeText,
                )
            )
            _uiState.update { it.copy(challengeSolvedCount = it.challengeSolvedCount + 1) }
            nextChallenge()
        } else {
            // Animate the UI
        }
    }

    private fun startChallenge() {
        challengeTimer.start()
        nextChallenge()
    }

    private fun nextChallenge() {
        if (_uiState.value.challengeSolvedCount < NB_CHALLENGE) {
            _uiState.update {
                val challenge: Challenge = getChallengeUseCase.invoke()
                it.copy(
                    challengeText = challenge.challengeText,
                    result = challenge.result,
                    resultOptions = challenge.resultOptions,
                )
            }
        } else {
            endChallenge()
        }
    }

    private fun endChallenge() {
        challengeTimer.stop()
        val challengeResults = ChallengeResults(
            totalTime = formatMillisToMMSS(millis = challengeTimer.getTotalTime()),
            results = currentResults.toList(),
        )
        currentResults.clear()
        _uiState.update {
            it.copy(
                challengeResults = challengeResults,
                isChallengeEnded = true,
            )
        }
    }

    private fun formatMillisToMMSS(millis: Long): String {
        val seconds = (millis / 1000) % 60
        val minutes = (millis / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun formatMillisToMSS(millis: Long): String {
        val totalSeconds = millis / 1000
        val remainingMillis = millis % 1000
        return String.format("%d.%03d s", totalSeconds, remainingMillis)
    }

    override fun onCleared() {
        challengeTimer.clear()
        super.onCleared()
    }

    companion object {
        const val NB_CHALLENGE: Int = 5
    }
}