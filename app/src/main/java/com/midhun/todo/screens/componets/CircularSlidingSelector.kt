package com.midhun.todo.screens.componets

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun CircularSlidingSelector(
    modifier: Modifier = Modifier,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("Pending", "Completed")
    val selectedIndex = options.indexOf(selectedOption)

    val transition = updateTransition(targetState = selectedIndex, label = "Selector Transition")
    val offsetX by transition.animateFloat(
        transitionSpec = { tween(durationMillis = 300) },
        label = "Offset Animation"
    ) { index ->
        index * 100f  // Adjust width for proper alignment
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .size(120.dp) // Adjust size for a circular shape
            .background(Color.Gray.copy(alpha = 0.3f))
            .clickable { onOptionSelected(if (selectedOption == "Pending") "Completed" else "Pending") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .offset(x = offsetX.dp)
                    .background(Color.Blue)
            )

            options.forEach { option ->
                Text(
                    text = option,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onOptionSelected(option) },
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}