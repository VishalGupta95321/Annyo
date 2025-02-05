package com.app.ggg

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
fun DislikeScreen(

) {
    val purple_haze = Color(0xFFC3ACD0)
    val violet = Color(0xFF674188)
    val beige = Color(0xFFF7EFE5)
    val light_beige = Color(0xFFFFFBF5)

    var isCLicked by remember {
        mutableStateOf(false)
    }

    val progress by animateFloatAsState(
        targetValue = if(isCLicked) 1f else 0f,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        ),
    )

    val context = LocalContext.current

    val motionScene = remember {
        context
            .resources.openRawResource(R.raw.dislike_screen_motion_scene)
            .readBytes()
            .decodeToString()
    }

    MotionLayout(
        progress = progress,
        motionScene = MotionScene(content = motionScene),
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .layoutId(LayoutIdDislikeScreen.Container.id)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            purple_haze, light_beige
                        )
                    )
                )
        ) {}
        LogoDislikeScreen(
            layoutId = LayoutIdDislikeScreen.Logo,
            drawable = R.drawable.dislike_button_logo
        )
    }

    Button(
        onClick = {
            isCLicked = !isCLicked
        }) {}
}


@Composable
fun LogoDislikeScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdDislikeScreen,
    @DrawableRes drawable: Int
) {
    Image(
        modifier = Modifier
            .padding(10.dp)
            .layoutId(layoutId.id)
            .then(modifier),
        painter = painterResource(id = drawable),
        contentDescription = null
    )
}

sealed class LayoutIdDislikeScreen(
    val id : String
){
    object Container : LayoutIdDislikeScreen("box")
    object Logo : LayoutIdDislikeScreen("logo")
}

@Preview
@Composable
fun p5(){
    DislikeScreen()
}