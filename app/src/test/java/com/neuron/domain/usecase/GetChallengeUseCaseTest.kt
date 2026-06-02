package com.neuron.domain.usecase

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GetChallengeUseCaseTest {

    private val useCase = GetChallengeUseCase()

    // Run each property-based check over many samples to account for randomness.
    private val iterations = 500

    @Test
    fun `invoke returns a non-null Challenge`() {
        val challenge = useCase()
        assertNotNull(challenge)
    }

    @Test
    fun `challengeText matches the pattern a + b = ?`() {
        repeat(iterations) {
            val challenge = useCase()
            assertTrue(
                "challengeText '${challenge.challengeText}' does not match expected pattern",
                challenge.challengeText.matches(Regex("""\d+ \+ \d+ = \?"""))
            )
        }
    }

    @Test
    fun `operands are within the 1 to 100 range`() {
        repeat(iterations) {
            val challenge = useCase()
            val (a, b) = parseOperands(challenge.challengeText)
            assertTrue("operand a=$a is out of range", a in 1..100)
            assertTrue("operand b=$b is out of range", b in 1..100)
        }
    }

    @Test
    fun `result equals the sum of the two operands`() {
        repeat(iterations) {
            val challenge = useCase()
            val (a, b) = parseOperands(challenge.challengeText)
            assertEquals(
                "result ${challenge.result} != $a + $b",
                a + b,
                challenge.result
            )
        }
    }

    @Test
    fun `resultOptions always contains exactly 3 elements`() {
        repeat(iterations) {
            val challenge = useCase()
            assertEquals(
                "expected 3 options but got ${challenge.resultOptions.size}",
                3,
                challenge.resultOptions.size
            )
        }
    }

    @Test
    fun `resultOptions always contains the correct answer`() {
        repeat(iterations) {
            val challenge = useCase()
            assertTrue(
                "correct answer ${challenge.result} not found in options ${challenge.resultOptions}",
                challenge.result in challenge.resultOptions
            )
        }
    }

    @Test
    fun `resultOptions contains no duplicate values`() {
        repeat(iterations) {
            val challenge = useCase()
            val distinct = challenge.resultOptions.distinct()
            assertEquals(
                "options contain duplicates: ${challenge.resultOptions}",
                3,
                distinct.size
            )
        }
    }

    @Test
    fun `all options are non-negative`() {
        repeat(iterations) {
            val challenge = useCase()
            challenge.resultOptions.forEach { option ->
                assertTrue(
                    "negative option $option found in ${challenge.resultOptions}",
                    option >= 0
                )
            }
        }
    }

    @Test
    fun `wrong answers differ from the correct answer by at most 10`() {
        repeat(iterations) {
            val challenge = useCase()
            val wrongAnswers = challenge.resultOptions.filter { it != challenge.result }
            wrongAnswers.forEach { wrong ->
                val diff = Math.abs(wrong - challenge.result)
                assertTrue(
                    "wrong answer $wrong is more than 10 away from correct ${challenge.result}",
                    diff in 1..10
                )
            }
        }
    }

    @Test
    fun `wrong answers are never equal to the correct answer`() {
        repeat(iterations) {
            val challenge = useCase()
            val wrongAnswers = challenge.resultOptions.filter { it != challenge.result }
            assertEquals(
                "expected exactly 2 wrong answers in ${challenge.resultOptions}",
                2,
                wrongAnswers.size
            )
        }
    }

    @Test
    fun `successive invocations can produce different challenges`() {
        val challenges = (1..20).map { useCase() }
        val uniqueTexts = challenges.map { it.challengeText }.toSet()
        assertTrue(
            "all 20 successive challenges were identical — randomness may be broken",
            uniqueTexts.size > 1
        )
    }

    // ---------------------------------------------------------------------------

    private fun parseOperands(challengeText: String): Pair<Int, Int> {
        // Format: "a + b = ?"
        val parts = challengeText.removeSuffix("= ?").trim().split("+")
        return parts[0].trim().toInt() to parts[1].trim().toInt()
    }
}
