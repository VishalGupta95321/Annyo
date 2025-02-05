package com.example.ui

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StandardButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    containerColor : Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
    content: @Composable (RowScope.() -> Unit),
) {
    Button(
        modifier = modifier
            .clip(RoundedCornerShape(35.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isEnabled) containerColor else Color.Gray ,
            contentColor = MaterialTheme
                .colorScheme.primaryContainer
        ),
        enabled = isEnabled,
        onClick = onClick
    ){content()}
}
