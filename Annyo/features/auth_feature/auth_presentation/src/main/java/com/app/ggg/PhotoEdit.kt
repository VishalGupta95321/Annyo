package com.app.ggg

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.auth_presentation.R

@Composable
fun PhotoEdit() {
    Box(modifier = Modifier.fillMaxSize()){
        var r by remember {
            mutableStateOf(0f)
        }
        var s by remember {
            mutableStateOf(1f)
        }
        var offset by remember {
            mutableStateOf(Offset.Zero)
        }
        Image(
            painter = painterResource(
                id = R.drawable.jon_ly_uipjy2xrojq_unsplash
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.translationX = offset.x
                    this.translationY = offset.y
                 //   this.rotationZ = r
                    this.scaleX = s
                    this.scaleY = s
                }
                .pointerInput(Unit) {
                    detectTransformGestures { centroid, pan, zoom, rotation ->
                        offset += pan
                        s *= zoom
                        r += rotation
                    }
                }
        )
    }
}

@Preview
@Composable
fun PPPPPP() {
    PhotoEdit()
}