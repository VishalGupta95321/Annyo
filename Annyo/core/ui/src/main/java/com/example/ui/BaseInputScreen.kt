package com.example.ui

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BaseInputScreen(
    isBackButtonEnabled: Boolean = true,
    isNextButtonEnabled:Boolean = true,
    label1: String? = null,
    label2: String? = null,
    inputContent: @Composable () -> Unit,
    onVisibilityToggle: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onNextCLick: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    var buttonAnimation by remember {
        mutableStateOf(false)
    }

    Column( modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        if (isBackButtonEnabled){
        Icon( modifier = Modifier
            .clickable { onBackClick() }
            .padding(10.dp)
            .size(35.dp),
            tint = MaterialTheme.colorScheme.primary,
            imageVector = ImageVector
                .vectorResource(id = R.drawable.back_arrow) ,
            contentDescription = null
        )}

        if (label1 != null){
          Text( modifier = Modifier
            .padding(10.dp),
            text = label1,
            style = MaterialTheme.typography.headlineMedium
                .copy(color = MaterialTheme.colorScheme.primary),
        )}

        if (label2 != null){
            Text( modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 5.dp, bottom = 10.dp),
                text = label2,
                style = MaterialTheme.typography.titleMedium
                    .copy(color = MaterialTheme.colorScheme.secondary)
            )}

       Column( modifier = Modifier
               .height(400.dp)
               .weight(1f)) { inputContent()
       }

        Box( modifier = Modifier
                .fillMaxWidth()
        ) {
            StandardButton( modifier = Modifier
                .animateOnBoardingButton(buttonAnimation, screenWidth)
                .align(Alignment.BottomEnd)
                .padding(horizontal = 18.dp, vertical = 10.dp),
                isEnabled = isNextButtonEnabled,
                onClick = {
                    buttonAnimation = !buttonAnimation
                    scope.launch {
                        delay(100)
                        onNextCLick()
                    }
                }
            ) { Text(
                text = "next",
                style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}


fun Modifier.animateOnBoardingButton(
    isStarted: Boolean = false,
    screeWidth: Float
): Modifier = composed {
    var targetValue by remember {
        mutableFloatStateOf(-screeWidth) // todo edited
    }
    val translationX by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    LaunchedEffect(true) {
        delay(300)
        targetValue = 0f
    }
    if (isStarted)
        targetValue = screeWidth

    graphicsLayer {
        this.translationX = translationX
    }
}

