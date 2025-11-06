package com.neuron.ui.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
private fun BackDialogPreview() {
    BackDialog(
        onDismiss = {},
        onNavigateBack = {}
    )
}

@Composable
fun BackDialog(
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