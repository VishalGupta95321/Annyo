package com.app.ggg.explore_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R

@Composable
fun ButtonDock(
    modifier: Modifier = Modifier,
) {
    val logo = listOf(
        R.drawable.like_button_logo,
        R.drawable.instant_match_button_icon,
        R.drawable.dislike_button_logo
    )
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.080f)
            .then(modifier)
    ) {
        Row(modifier = Modifier.fillMaxWidth(0.6f)) {
            logo.forEach {
                Icon(
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .weight(1f),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = it),
                    contentDescription = null
                )
            }
        }
    }
}