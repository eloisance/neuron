package com.neuron.ui.composable

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    ChallengeScreen(
        onNavigateBack = {},
    )
}

@Composable
fun ChallengeScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ChallengeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()
    var showBackDialog by remember { mutableStateOf(false) }

    BackHandler(
        enabled = true,
        onBack = {
            showBackDialog = true
        }
    )

    if (showBackDialog) {
         BackDialog(
             onDismiss = {
                 showBackDialog = false
             },
             onNavigateBack = onNavigateBack,
         )
    }

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

@Composable
private fun BackDialog(
    onDismiss:() -> Unit,
    onNavigateBack: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss.invoke()
        },
        title = { Text("Stop this challenge ?") },
        text = { Text("Are you sure you want to stop this challenge ?") },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss.invoke()
                    onNavigateBack.invoke()
                }
            ) {
                Text("Yes")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss.invoke()
                }
            ) {
                Text("No")
            }
        }
    )
}