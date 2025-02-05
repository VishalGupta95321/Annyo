package com.app.ggg

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.app.auth_presentation.R


@OptIn(ExperimentalMotionApi::class)
@Composable
fun Dislike(
    progress : Float

) {

    val c1 = Color(0xFFaa00ff)
    val c2 =  Color(0xFFd500f9)
    val c3=  Color(0xFFe040fb)
    val c4 =  Color(0xFFea80fc)

    val c5 =  Color(0xFF4a148c)
    val c6 =  Color(0xFF6a1b9a)
    val c7 =  Color(0xFF7b1fa2)
    val c8 =  Color(0xFF8e24aa)
    val c9 =  Color(0xFF9c27b0)
    val c10 =  Color(0xFFab47bc)
    val c11 =  Color(0xFFba68c8)
    val c12 =  Color(0xFFce93d8)
    val c13 =  Color(0xFFe1bee7)
    val c14 =  Color(0xFFf3e5f5)
//    var isCLicked by  remember {
//        mutableStateOf(false)
//    }
//
//    val progress by animateFloatAsState(
//        targetValue = if (isCLicked) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = LinearOutSlowInEasing
//        ))

    val context = LocalContext.current

    val motionScene = remember {
        context
            .resources.openRawResource(R.raw.disli)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        progress = progress,
        motionScene = MotionScene(content = motionScene),
        modifier = Modifier.fillMaxWidth()
    ) {

        val properties = motionProperties("dislike_screen")

        Box(
            modifier = Modifier
                .layoutId("dislike_screen")
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(properties.value.int("corner_radius").dp)
                        .copy(
                            topEnd = ZeroCornerSize,
                            topStart = ZeroCornerSize,
                            bottomStart = ZeroCornerSize
                        )
                )
                .background(color = c5)
        ) {}


    }
//    Button(onClick = { isCLicked = !isCLicked}) {
//
//    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun DislikeButton (
    progress : Float
){

//    var isCLicked by  remember {
//        mutableStateOf(false)
//    }
//
//    val progress by animateFloatAsState(
//        targetValue = if (isCLicked) 1f else 0f,
//        animationSpec = tween(
//            durationMillis = 500,
//            easing = LinearOutSlowInEasing
//        ))

    val context = LocalContext.current

    val motionScene = remember {
        context
            .resources.openRawResource(R.raw.dislike)
            .readBytes()
            .decodeToString()
    }

        MotionLayout(
            progress = progress,
            motionScene = MotionScene(content = motionScene),
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                    modifier = Modifier
                        .layoutId("dislike_button")
                        .clip(CircleShape)
                        .background(Color.Red)
                        .size(100.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.google_logo),
                        contentDescription = null
                    )
                }

            Button(
                modifier = Modifier
                    .layoutId("dislike_button2")
                    .clip(CircleShape)
                    .background(Color.Red)
                    .size(100.dp),
                onClick = { /*TODO*/ }
            ){
                Icon(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = null
                )
            }


            }
//    Button(onClick = { isCLicked = !isCLicked }) {
//
//    }
}

@Composable
fun Dislike(){


    var isCLicked by  remember {
        mutableStateOf(false)
    }

    val progress by animateFloatAsState(
        targetValue = if (isCLicked) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        ))

    Box(modifier = Modifier.fillMaxSize()){
        Dislike(progress = progress)
        DislikeButton(progress = progress)
        Button(onClick = { isCLicked = !isCLicked }) {
        }
    }
}

@Composable
fun C (){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Blue)
    ){

            Button(
                modifier = Modifier
                    .padding(20.dp)
                    .width(300.dp)
                    .height(300.dp)
                    .graphicsLayer {
//                        this.translationX = 100f
//                        this.rotationX = 180f
//                        this.rotationY = 180f
                        this.rotationZ = -15f
                    }
                ,
                onClick = { /*TODO*/ }
            ) {
                Image(painter = painterResource(id = R.drawable.register_screen_couple_image)
                    , contentDescription = null )
            }

        Button(onClick = {

        }) {
//            rotation =  animateFloatAsState(
//                targetValue = 180f,
//                animationSpec = tween(
//                    durationMillis = 500,
//                    easing = LinearOutSlowInEasing
//                ))
    }
    }
}

@Composable
fun ModifierGraphicsLayerModifierRotation() {
    // [START android_compose_graphics_modifiers_graphicsLayer_rotation]
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.register_screen_couple_image),
            contentDescription = "Sunset",
            modifier = Modifier
                .graphicsLayer {
                   this.rotationX = 180f
                    this.rotationY = 180f
                    this.rotationZ = 400f
                }
        )
    }
    // [END android_compose_graphics_modifiers_graphicsLayer_rotation]
}


@Preview(showBackground = true)
@Composable
fun P3(){
   //ModifierGraphicsLayerModifierRotation()
    C()
}