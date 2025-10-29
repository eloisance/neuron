package com.neuron.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.neuron.ui.viewmodel.ChallengeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Preview
@Composable
fun ChallengeScreenPreview() {
    ChallengeScreen()
}

@Composable
fun ChallengeScreen(
    modifier: Modifier = Modifier,
    viewModel: ChallengeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = state.challengeText,
            fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            state.resultOptions.forEach { option ->
                Button(onClick = {
                    viewModel.answer(optionChosen = option)
                }) {
                    Text(text = option.toString())
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Challenge solved : ${state.challengeSolvedCount}",
            color = Color.DarkGray,
            fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
        )
    }
}
