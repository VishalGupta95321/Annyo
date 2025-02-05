package com.app.auth_presentation.otp_login.otp_login_screen.animations

import android.annotation.SuppressLint
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import kotlinx.coroutines.delay

@SuppressLint("SuspiciousIndentation")
fun Modifier.animateOtpTextField(
    textFieldCount:Int,
    startAnimation:Boolean = false
): Modifier = composed {

    var targetValue by remember {
        mutableStateOf(0f)
    }

    val translationY by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = SpringSpec(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    if (startAnimation)
        LaunchedEffect(true ){
            when(textFieldCount){
                0 -> {
                    delay(300)
                }
                1 -> {
                    delay(400)
                }
                2 -> {
                    delay(500)
                }
                3 -> {
                    delay(600)
                }
                4 -> {
                    delay(700)
                }
                5 -> {
                    delay(800)
                }
            }
            targetValue = -80f
            delay(300)
            targetValue = 0f
        }

    graphicsLayer {
        this.translationY = translationY
    }
}
