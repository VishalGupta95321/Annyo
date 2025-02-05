package com.app.auth_presentatio

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R

@Composable
fun BottomAppBar(
    modifier: Modifier = Modifier
) {
    var selected by remember {
        mutableStateOf("")
    }

    val icons = listOf(
        R.drawable.a_icon,
        R.drawable.like_icon,
        R.drawable.chat_icon,
        R.drawable.user_profile_icon
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.065f)
            .then(modifier)
    ) {
        icons.forEach { iconId ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.primary)
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier
                        .padding(13.dp)
                        .clickable { selected = iconId.toString() }, // todo indication
                    tint = if (selected == iconId.toString())
                        MaterialTheme.colorScheme.primaryContainer
                    else Color(0xFF785F83),
                    painter = painterResource(id = iconId),
                    contentDescription = null
                )
            }
        }
    }

}

@Preview
@Composable
fun p12() {
    BottomAppBar()
}