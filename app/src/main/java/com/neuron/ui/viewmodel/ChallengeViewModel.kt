package com.neuron.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.neuron.domain.model.Challenge
import com.neuron.domain.usecase.GetChallengeUseCase
import com.neuron.ui.model.ChallengeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ChallengeViewModel(
    private val getChallengeUseCase: GetChallengeUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChallengeUiState())
    val uiState: StateFlow<ChallengeUiState> = _uiState.asStateFlow()

    init {
        println("ChallengeViewModel : init")
        startChallenge()
    }

    fun answer(optionChosen: Int) {
        if (optionChosen == _uiState.value.result) {
            _uiState.update { it.copy(challengeSolvedCount = it.challengeSolvedCount + 1) }
            nextChallenge()
        } else {
            // Animate the UI
        }
    }

    private fun startChallenge() {
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
        // TODO: Save record in history
        _uiState.update { it.copy(isChallengeEnded = true) }
    }

    override fun onCleared() {
        println("ChallengeViewModel : onCleared")
        super.onCleared()
    }

    private companion object {
        const val NB_CHALLENGE: Int = 2
    }
}