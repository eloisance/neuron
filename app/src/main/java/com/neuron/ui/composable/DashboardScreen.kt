package com.neuron.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun DashboardScreenPreview() {
    DashboardScreen(onNavigateToChallenge = {})
}

@Composable
fun DashboardScreen(
    onNavigateToChallenge: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Neuron",
            fontSize = TextUnit(value = 24f, type = TextUnitType.Sp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onNavigateToChallenge.invoke()
        }) {
            Text(text = "Start a challenge")
        }
    }
}