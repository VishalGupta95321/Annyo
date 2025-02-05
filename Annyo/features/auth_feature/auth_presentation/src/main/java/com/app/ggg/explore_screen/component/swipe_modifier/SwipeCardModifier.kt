package com.app.ggg.explore_screen.component.swipe_modifier

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.launch

fun Modifier.swipeCard(
    state: SwipeState,
    horizontalSwipeEnabled: Boolean,
    verticalSwipeEnabled: Boolean,
    onSwipedRight: () -> Unit = {},
    onSwipedLeft: () -> Unit = {},
    onSwipedUp: () -> Unit = {},
    onSwipedDown: () -> Unit = {}
) = composed {
    val scope = rememberCoroutineScope()
    pointerInput(Unit) {
        detectDragGestures(
            onDrag = { change: PointerInputChange, dragAmount: Offset ->
                scope.launch {
                    state.drag(
                        if (horizontalSwipeEnabled)
                            dragAmount.x
                        else 0f,
                        if (verticalSwipeEnabled)
                            dragAmount.y
                        else 0f
                    )
                }
                change.consume()
            },
            onDragEnd = {
                if (horizontalSwipeEnabled)
                    scope.launch {
                        when (state.swipeHorizontal()) {
                            SwipeDirection.Left -> {
                                onSwipedLeft()
                            }
                            SwipeDirection.Right -> {
                                onSwipedRight()
                            }
                            else -> {}
                        }
                    }
                if (verticalSwipeEnabled)
                    scope.launch {
                        when (state.swipeVertical()) {
                            SwipeDirection.Up -> {
                                onSwipedUp()
                            }
                            SwipeDirection.Down -> {
                                onSwipedDown()
                            }
                            else -> {}
                        }
                    }
            },
            onDragCancel = {
                scope.launch {
                    state.reset()
                }
            }
        )
    }
        .offset{ IntOffset(state.offset().x.toInt(),state.offset().y.toInt()) }
        .rotate(state.rotateZ())
}

