package com.example.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun ChipsGroup(
    modifier: Modifier = Modifier,
    choices: ChipGroupChoices,
    selectedOptionOrOptions: List<String>,
    onSelect: (String) -> Unit
) {
    FlowRow(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        choices.getChoices().forEach { text ->
            Chips(
                modifier = Modifier,
                isSelected = selectedOptionOrOptions.contains(text),
                text = text,
                onSelect = onSelect
            )
        }
    }
}

@Composable
fun Chips(
    modifier: Modifier = Modifier,
    text: String,
    isSelected: Boolean = true,
    onSelect: (String) -> Unit = {}
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .animateBackgroundColor(
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(40.dp)
            )
            .clip(RoundedCornerShape(40.dp))
            .clickable { onSelect(text) }
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(40.dp)
            )

    ) {
        Text(
            modifier = Modifier
                .padding(15.dp),
            text = text,
            color = if (isSelected)
                MaterialTheme.colorScheme.secondaryContainer
            else
                MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium
        )
    }
}


fun Modifier.animateBackgroundColor(
    color: Color,
    shape: Shape = RectangleShape
): Modifier =
    composed {
        val backgroundColor by animateColorAsState(
            targetValue = color,
            animationSpec = tween(
                durationMillis = 300,
                easing = LinearOutSlowInEasing
            )
        )
        background(
            color = backgroundColor,
            shape = shape
        )
    }
