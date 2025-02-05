package com.app.ggg.explore_screen.component.swipe_modifier

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun rememberSwipeState(): SwipeState {

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

    return remember {
        SwipeState(screenHeight, screenWidth)
    }
}

class SwipeState(
    private val screenHeight: Float,
    private val screenWidth: Float
) {

    private var offsetX = Animatable(0f)
    // private set
    private var offsetY = Animatable(0f)

    suspend fun swipeVertical(): SwipeDirection? {      /// todo use velocity in animation to swipe the card if user swipe fast
        return if (offsetY.value >= screenHeight / 2.5) {
            swipe(SwipeDirection.Up)
            SwipeDirection.Up
        } else if (offsetY.value <= -screenHeight / 2.5) {
            swipe(SwipeDirection.Down)
            SwipeDirection.Down
        } else {
            offsetY.animateTo(0f, tween(300))
            null
        }
    }

    suspend fun swipeHorizontal(): SwipeDirection? {
        return if (offsetX.value >= screenWidth / 2) {
            swipe(SwipeDirection.Right)
            SwipeDirection.Right
        } else if (offsetX.value <= -screenWidth / 2) {
            swipe(SwipeDirection.Left)
            SwipeDirection.Left
        } else {
            offsetX.animateTo(0f, tween(300))
            null
        }
    }

    suspend fun swipe(direction: SwipeDirection) {
        when (direction) {
            SwipeDirection.Left -> {
                offsetX.animateTo(-screenWidth * 2, tween(100))
            }
            SwipeDirection.Right -> {
                offsetX.animateTo(screenWidth * 2, tween(100))
            }
            SwipeDirection.Up -> {
                offsetY.animateTo(screenHeight * 2, tween(100))
            }
            SwipeDirection.Down -> {
                offsetY.animateTo(-screenHeight * 2, tween(100))
            }
        }
    }

    suspend fun reset() {
        offsetX.snapTo(0f)
        offsetY.snapTo(0f)
    }

    suspend fun drag(x: Float, y: Float) {
        offsetX.snapTo(offsetX.value + x)
        offsetY.snapTo(offsetY.value + y)
    }

    fun rotateZ(): Float {
        return (offsetX.value / 40).coerceIn(-40f, 40f)
    }

    fun offset(): Offset {
        return Offset(x = offsetX.value, y = offsetY.value)
    }
}

enum class SwipeDirection {
    Up,
    Down,
    Left,
    Right
}