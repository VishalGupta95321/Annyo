package com.example.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StandardTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    placeholderText: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    maxLines: Int = 1,
) {
    TextField(
        modifier = modifier
            .clip(RoundedCornerShape(40.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(40.dp)
            ),
        colors = TextFieldDefaults.textFieldColors(    // FIXME: update this
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.secondary,
        ),
        singleLine = singleLine,
        maxLines = maxLines,
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType
        ),
        value = value,
        placeholder = {
            Text(
                text = placeholderText,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Gray
            )
        },
        onValueChange = onValueChange,
    )
} /// todo maybe add leading and trailing icon option