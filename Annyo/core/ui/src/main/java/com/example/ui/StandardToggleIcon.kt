package com.example.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun StandardToggleIcon(
    modifier: Modifier = Modifier,
    @DrawableRes iconBeforeToggle: Int,
    @DrawableRes iconAfterToggle: Int,
    textBeforeToggle: String,
    textAfterToggle: String,
    onToggle: () -> Unit,
) {
    var isToggled by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    isToggled = !isToggled
                    onToggle()
                }
                .padding(horizontal = 10.dp)
                .size(40.dp),
            painter = painterResource(
                id = if (isToggled)
                    iconAfterToggle
                else iconBeforeToggle
            ),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Text(
            text = if (isToggled)
                textAfterToggle
            else textBeforeToggle,
            style = MaterialTheme.typography.titleSmall
                .copy(
                    color = MaterialTheme.colorScheme.primary
                )
        )
    }
}

// todo *&*()^&*)&^980^