package com.app.ggg.explore_screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HeadingLabel(
    text: String
) {
    Text(
        modifier = Modifier.padding(vertical = 10.dp),
        text = text,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontSize = 19.sp
        )
    )
}
