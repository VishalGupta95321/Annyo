package com.example.profile_presentation.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import kotlinx.coroutines.delay


fun Modifier.addAnimatedProgressIndicator(
    indicatorColor: Color,
    percentageCompleted: Float,
    maxSweep : Float  = 240f
) = composed {

    var filledSweep by remember {
        mutableStateOf(0f)
    }

    LaunchedEffect(true){
        delay(100)
        filledSweep = percentageCompleted
    }

    val sweepPercentage = (filledSweep / 100) * 100


    val sweepAngle by animateFloatAsState(
        targetValue = (maxSweep/100) * sweepPercentage,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    drawBehind {
        progressIndicator(
            color = Color.LightGray,
        )
        progressIndicator(
            color = indicatorColor,
            sweepAngle = sweepAngle
        )
    }
}


fun DrawScope.progressIndicator(
    color : Color,
    indicatorWidth:Float = 30f,
    sweepAngle:Float = 240f
){
    drawArc(
        color = color,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(0f,0f),
    )
}