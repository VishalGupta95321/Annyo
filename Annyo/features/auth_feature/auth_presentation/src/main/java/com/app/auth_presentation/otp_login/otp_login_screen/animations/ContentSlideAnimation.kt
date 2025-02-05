package com.app.auth_presentation.otp_login.otp_login_screen.animations

import androidx.compose.animation.*
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ContentSlideAnimation(
    modifier: Modifier = Modifier,
    duration: Int,
    startAnimation: Boolean,
    content1: @Composable () -> Unit,
    content2: @Composable () -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = startAnimation,
        transitionSpec = {
            if (startAnimation) {
                slideInHorizontally(
                    animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
                ) { width -> (width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = duration,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> -(width * 2) } + fadeOut()
            } else {
                slideInHorizontally(
                    animationSpec = tween(durationMillis = duration, easing = LinearOutSlowInEasing)
                ) { width -> -(width * 2) } + fadeIn() with
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = duration,
                                easing = LinearOutSlowInEasing
                            )
                        ) { width -> (width * 2) } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        }
    ) { OtpVerifyScreen ->
        if (!OtpVerifyScreen)
            content1()
        else
            content2()
    }
}
