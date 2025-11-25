package com.neuron.ui.composable

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChallengeProgressionBar(
    solvedCount: Int,
    totalChallenges: Int,
    modifier: Modifier = Modifier
) {
    val animatedSolvedCount by animateFloatAsState(
        targetValue = solvedCount.toFloat(),
        animationSpec = tween(durationMillis = 300),
        label = "Progression"
    )
    val solvedColor = MaterialTheme.colorScheme.primary
    val unsolvedColor = MaterialTheme.colorScheme.surfaceVariant
    val separatorColor = MaterialTheme.colorScheme.background
    Row(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(Color.LightGray)
    ) {
        repeat(times = totalChallenges) { index ->
            val fillFraction = if (index < animatedSolvedCount) {
                (animatedSolvedCount - index).coerceIn(0f, 1f)
            } else {
                0f
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(unsolvedColor)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(fillFraction)
                        .background(solvedColor)
                )
            }
            if (index < totalChallenges - 1) {
                Spacer(modifier = Modifier
                    .width(2.dp)
                    .fillMaxHeight()
                    .background(separatorColor)
                )
            }
        }
    }
}