package com.app.ggg.explore_screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R


@Composable
fun NameCard(
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.titleLarge.copy(
        color = MaterialTheme.colorScheme.primary
    ),
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = "Taylor Brook",
            overflow = TextOverflow.Ellipsis,
            style = style
        )
        Icon(
            modifier = Modifier.size(30.dp),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(id = R.drawable.verify_icon),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(horizontal = 10.dp),
            text = "21",
            style = style
        )
    }
}