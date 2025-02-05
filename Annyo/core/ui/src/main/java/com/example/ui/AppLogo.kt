package com.example.ui

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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.R

@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    logoSize: LogoSize
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        modifier = modifier
            .padding(5.dp)
    ){
        Icon(
            modifier = Modifier.size(logoSize.iconSize),
            tint = MaterialTheme.colorScheme.primary,
            painter = painterResource(id = R.drawable.a_icon),
            contentDescription = null
        )
        Text(
            text = "nnyo",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = logoSize.textSIze
            ),
        )
    }
}


enum class LogoSize(
      val iconSize:Dp,
      val textSIze:TextUnit
) {
    NORMAL(
        iconSize = 60.dp,
        textSIze = 45.sp
    ),
    SMALL(
        iconSize = 50.dp,
        textSIze = 35.sp
    )
}