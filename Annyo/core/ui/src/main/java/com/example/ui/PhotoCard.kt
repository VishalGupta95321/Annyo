package com.example.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter


@Composable
fun PhotoCard(
    modifier: Modifier = Modifier,
    model: Any,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(30.dp)
            )
            .height(150.dp)
            .width(110.dp)
            .clip(RoundedCornerShape(30.dp))
    ) {
        Image(
            modifier = Modifier
                .alpha(if (model is Uri) 1f else 0.5f)
                .clickable { onClick() },
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = model,
                contentScale = ContentScale.Crop
            ),
            contentDescription = null,
        )
    }
}
