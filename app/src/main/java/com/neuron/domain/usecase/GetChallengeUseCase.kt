package com.neuron.domain.usecase

import com.neuron.domain.model.Challenge

class GetChallengeUseCase() {

    operator fun invoke(): Challenge {
        return generateChallenge()
    }

    /**
     * Improve logic here, more tricky wrong answers, include more operators, etc..
     */
    private fun generateChallenge(): Challenge {
        val range = 1..100
        val a = range.random()
        val b = range.random()
        val correctResult = a + b
        val options = mutableSetOf(correctResult)
        while (options.size < 3) {
            val offset = (-10..10).random()
            if (offset != 0) {
                val incorrectAnswer = correctResult + offset
                if (incorrectAnswer >= 0) {
                    options.add(incorrectAnswer)
                }
            }
        }
        return Challenge(
            challengeText = "$a + $b = ?",
            result = correctResult,
            resultOptions = options.toList().shuffled()
        )
    }
}
