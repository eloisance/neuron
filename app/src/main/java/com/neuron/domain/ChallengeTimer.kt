package com.neuron.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Manages the timing for a challenge, including total elapsed time and time taken per answer.
 * This class is pure business logic and lives in the domain layer.
 */
class ChallengeTimer {

    private var startTimeMillis: Long = 0L
    private var lastAnswerTimeMillis: Long = 0L
    private var timerJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Default)

    private val _isTimerRunning = MutableStateFlow(false)
    val isTimerRunning: StateFlow<Boolean> = _isTimerRunning.asStateFlow()

    private val _elapsedTimeMillis = MutableStateFlow(0L)
    val elapsedTimeMillis: StateFlow<Long> = _elapsedTimeMillis.asStateFlow()

    /**
     * Starts the challenge timer.
     * If the timer is already running, this function does nothing.
     * Otherwise, it resets the timer state, records the start time,
     * and launches a coroutine to update the elapsed time every 100 milliseconds.
     */
    fun start() {
        if (isTimerRunning.value) return

        reset()
        startTimeMillis = System.currentTimeMillis()
        lastAnswerTimeMillis = startTimeMillis
        _isTimerRunning.value = true

        timerJob = scope.launch {
            while (isActive) {
                val currentMillis = System.currentTimeMillis()
                _elapsedTimeMillis.value = currentMillis - startTimeMillis
                delay(100L)
            }
        }
    }

    /**
     * Stops the timer.
     *
     * If the timer is already stopped, this function does nothing. Otherwise, it cancels the
     * coroutine job that updates the elapsed time and sets the timer's running state to false.
     * The final elapsed time is preserved in [elapsedTimeMillis].
     */
    fun stop() {
        if (!isTimerRunning.value) return

        timerJob?.cancel()
        timerJob = null
        _isTimerRunning.value = false
    }

    /**
     * Calculates and returns the time elapsed since the last answer was recorded.
     *
     * @return The time in milliseconds taken for the current answer.
     */
    fun getAnswerTime(): Long {
        val currentMillis = System.currentTimeMillis()
        val timeForCurrentAnswer = currentMillis - lastAnswerTimeMillis
        lastAnswerTimeMillis = currentMillis
        return timeForCurrentAnswer
    }

    /**
     * Resets the timer state.
     */
    fun reset() {
        stop()
        startTimeMillis = 0L
        lastAnswerTimeMillis = 0L
        _elapsedTimeMillis.value = 0L
    }

    /**
     * Provides the final total time when the timer is stopped.
     */
    fun getTotalTime(): Long {
        return _elapsedTimeMillis.value
    }

    /**
     * Cleans up the coroutine scope when the timer is no longer needed.
     * This is crucial as ChallengeTimer is 'factory' provided and not a singleton.
     */
    fun clear() {
        scope.cancel()
    }
}