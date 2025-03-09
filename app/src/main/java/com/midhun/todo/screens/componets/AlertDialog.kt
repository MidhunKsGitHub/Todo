package com.midhun.todo.screens.componets

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AlertDialog( shouldShowDialog: MutableState<Boolean>,onClick:()-> Unit) {
    if (shouldShowDialog.value) { // 2
        AlertDialog( // 3
            onDismissRequest = { // 4
                shouldShowDialog.value = false
            },
            // 5
            title = { Text(text = "Delete Task ") },
            text = { Text(text = "Do you want to delete this task") },
            confirmButton = { // 6
                Button(
                    onClick = {
                        onClick()
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button({}, colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)) {
                    Text(
                        text = "Cancel",
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            shouldShowDialog.value = false
                        }
                    )
                }
            }

        )
    }
}