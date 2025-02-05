package com.app.ggg

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.app.auth_presentation.R

@OptIn(ExperimentalMotionApi::class)
@Composable
fun Swipe (

){


    var click1 by remember {
        mutableStateOf(false)
    }
//
//    var click2 by remember {
//        mutableStateOf(false)
//    }
//
//    var Progress1 by remember {
//        mutableStateOf(0f)
//    }
//    var Progress2 by remember {
//        mutableStateOf(0f)
//    }

//    var progress =
//        animateFloatAsState(
//        targetValue =  ,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessVeryLow
//        ))
//    var progress2 = animateFloatAsState(
//        targetValue = Progress2,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessVeryLow
//        ))

    var transition by remember {
        mutableStateOf("left")
    }

    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }
    var translationX by remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(300) }
    var height by remember { mutableStateOf(400) }


    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                change.consume()
                val (x, y) = dragAmount
                when {
                    x < 0 -> {
                        transition = "left"
                        println(transition)
                    }
                    x > 0 -> {
                        transition = "right"
                        println(transition)
                    }

                }
            }
        },
    ){
//        println(transition)
//        if(transition=="left")
        SwipeCard("left")
      //  else SwipeCard(transition = "right")
    }


//    var progress1 by remember {
//        mutableStateOf(0f)
//    }
//    var progress2 by remember {
//        mutableStateOf(0f)
////    }
//    println(" progress 1  "+ progress1)
//    println(" progress 2  "+ progress2)
//
//    var color1 by remember {
//        mutableStateOf(Color.Blue)
//    }
//    var color2 by remember {
//        mutableStateOf(Color.Red)
//    }
//    val motionScene = remember {
//        context
//            .resources.openRawResource(R.raw.swipw_motion_scene)
//            .readBytes()
//            .decodeToString()
//    }
//
//    Box{
//
//        var offsetX by remember { mutableStateOf(0f) }
//        var offsetY by remember { mutableStateOf(0f) }
//
//        MotionLayout(
//            progress = if(Progress2==0f) Progress2 else progress2.value,
//            motionScene = MotionScene(content = motionScene),
//            modifier = Modifier.fillMaxWidth()
//        ) { Card(
//            modifier = Modifier
//                .padding(20.dp)
//                .layoutId("card")
//                .pointerInput(Unit) {
//                    detectDragGestures { change, dragAmount ->
//                        change.consume()
//                        val (x, y) = dragAmount
//                        when {
//                            x > 0 -> {}
//                            x < 0 -> {
//                                click2 = true
//                                Progress2 = 1f
//                                Progress1 = 0f
//                            }
//                        }
//                        when {
//                            y > 0 -> {}
//                            y < 0 -> {}
//                        }
//                    }
//                },
//            colors = CardDefaults.cardColors(
//                containerColor = color1
//            )) {} }
//
//        MotionLayout(
//            progress = if(Progress1==0f) Progress1 else progress1.value,
//            motionScene = MotionScene(content = motionScene),
//            modifier = Modifier.fillMaxWidth()
//        ) { Card(
//            modifier = Modifier
//                .padding(20.dp)
//                .layoutId("card")
//                .pointerInput(Unit) {
//                    detectDragGestures { change, dragAmount ->
//                        change.consume()
//                        val (x, y) = dragAmount
//                        when {
//                            x > 0 -> {
//
//                            }
//                            x < 0 -> {
//                                click1 = true
//                                Progress1 = 1f
//                                Progress2 = 0f
//                            }
//                        }
//                        when {
//                            y > 0 -> {}
//                            y < 0 -> {}
//                        }
//                    }
//                },
//            colors = CardDefaults.cardColors(
//                containerColor = color2
//            )) {} }
//    }
}

@Composable
fun anim(){
    val edcd by remember {
        mutableStateOf(
            Animatable(1f)
        )
    }
}
//
//    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
//        var offsetX by remember { mutableStateOf(0f) }
//        var offsetY by remember { mutableStateOf(0f) }
//        var translationx by remember { mutableStateOf(0f) }
//        var translationy by remember { mutableStateOf(0f) }
//        var rotationx by remember { mutableStateOf(0f) }
//        var rotationy by remember { mutableStateOf(0f) }
//        var rotationz by remember { mutableStateOf(0f) }
//        val minSwipeOffset by remember {
//            mutableStateOf(
//                constraints.maxWidth
//            )}
//        Box(
//            Modifier
//                .align(Alignment.Center)
//              //  .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
//                .size(300.dp, 400.dp)
//                .background(Color.Blue)

//                .pointerInput(Unit) {
//                    detectDragGestures(
//                        onDrag = { pointerInputChange, offset ->
//                            pointerInputChange.consume()
//                        val (x,y) = offset
//                        when {
//                            x > 0 ->{  translationx = 100f  }
//                            x < 0 ->{   translationy = offset.y*100f}
//                        }
//                        when {
//                            y > 0 -> {  rotationx = offset.x*100f }
//                            y < 0 -> {  rotationy = offset.y*100f  }
//                        }
////                            println("offset x"+offset.x+"    offset y"+offset.y)
//                        offsetX += offset.x
////                            if(offset.y==1f){
////                                println("swiped up ")
////                            }
//                        offsetY += offset.y
//                        },
//                        onDragStart = {
//
//                        },
//                        onDragEnd = {
//                            when {
//                                (offsetX < 0F && abs(offsetX) > minSwipeOffset) -> {
//                                    println("swiped left")
//                                }
//                                (offsetX > 0 && abs(offsetX) > minSwipeOffset) -> {
//                                    println("swiped right")
//                                }
//                                else -> null
//
//                            }
//                        },
//                        onDragCancel = {
//
//                        },
//                    )
//                }
//        )
//    }

fun swipeLeft() {
//    scope.apply {
//        launch {
//            offsetX.animateTo(-screenWidth, animationSpec)
//
//            onSwipeLeft()
//
//            launch {
//                offsetX.snapTo(center.x)
//            }
//
//            launch {
//                offsetY.snapTo(0f)
//            }
//
//            launch {
//                rotation.snapTo(0f)
//            }
//
//            launch {
//                scale.snapTo(0.8f)
//            }
//        }
//
//        launch {
//            scale.animateTo(1f, animationSpec)
//        }
//    }
}
@SuppressLint("UnrememberedAnimatable")
@OptIn(ExperimentalMotionApi::class)
@Composable
fun SwipeCard(
    transition:String
){
    val context = LocalContext.current

    var progress by remember {
        mutableStateOf(0f)
    }

//    var progress = animateFloatAsState(
//        targetValue = targetValue ,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessVeryLow
//        ))

    val screenWidth by remember {
        mutableStateOf(300)
    }
    val height by remember {
        mutableStateOf(400)
    }
    val right = Offset(screenWidth.toFloat(), 0f)
    val left = Offset(-screenWidth.toFloat(), 0f)
    val center = Offset(0f, 0f)
    val motionScene = remember {
        context
            .resources.openRawResource(R.raw.swipw_motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        progress = progress,
        motionScene = MotionScene(content = motionScene),
      //  transitionName =  transition,
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .width(screenWidth.dp)
                .height(height.dp)
                .padding(20.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        val (x, y) = dragAmount
                        when {
                            x < 0 -> {
                                println(x)
                              // progress = x
                            }
                            x > 0 -> {

                            }

                        }
                    }
                }
                .layoutId("card"),
            colors = CardDefaults.cardColors(
                containerColor = Color.Blue
            )
        ) {}
    }
}