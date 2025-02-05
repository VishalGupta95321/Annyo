package com.app.ggg.explore_screen.animted_match_screen

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.app.auth_presentation.R

@Composable
fun AnimatedMatchScreen(
    isMatched: Boolean
) {
    val animationProgress = animateFloatAsState(
        targetValue = if (isMatched) 1f else 0f,
        animationSpec = tween(
            durationMillis = 500,
            easing = LinearOutSlowInEasing
        )
    )
    Box {
        MatchScreenMotionLayout(
            progress = animationProgress.value
        )
    }
}

@OptIn(ExperimentalMotionApi::class)
@Composable
fun MatchScreenMotionLayout(
    progress: Float,
) {

    var text by remember {
        mutableStateOf("")
    }

    val context = LocalContext.current

    val motionScene = remember {
        context
            .resources.openRawResource(R.raw.like_screen_motion_scene)
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
                .layoutId(LayoutIdMatchedScreen.Container.id)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer
                )
        ) {}
        LabelMatchedScreen(
            layoutId = LayoutIdMatchedScreen.Label,
            text = "Match!"
        )
        ImageCardMatchedScreen(
            layoutId = LayoutIdMatchedScreen.ImageCard1,
            drawable = R.drawable.pet_icon
        )
        ImageCardMatchedScreen(
            layoutId = LayoutIdMatchedScreen.ImageCard2,
            drawable = R.drawable.pet_icon
        )
        TextFieldMatchedScreen(
            text = text,
            layoutId = LayoutIdMatchedScreen.TextField,
            onSendClicked = {}
        ) {
            text = it
        }
        ButtonMatchedScreen(
            text = "Discover More",
            layoutId = LayoutIdMatchedScreen.BackButton
        ) {}
        ButtonMatchedScreen(
            icon = R.drawable.share_icon,
            text = "Share",
            layoutId = LayoutIdMatchedScreen.ShareButton
        ) {}
        LogoMatchedScreen(
            layoutId = LayoutIdMatchedScreen.Logo,
            drawable = R.drawable.like_button_logo
        )
    }
}


@Composable
fun LabelMatchedScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdMatchedScreen,
    text: String,
) {
    Text(
        modifier = Modifier
            .layoutId(layoutId.id)
            .then(modifier),
        text = text,
        style = TextStyle(
            fontSize = 50.sp,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.sacramento)),
            shadow = Shadow(
                color = MaterialTheme.colorScheme.tertiary,
                offset = Offset(5.0f, 10.0f),
                //blurRadius = 3f
            )
        ),
    )
}

@Composable
fun LogoMatchedScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdMatchedScreen,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldMatchedScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdMatchedScreen,
    text: String,
    onSendClicked: () -> Unit,
    onValueChanged: (String) -> Unit
) {

    TextField(
        modifier = Modifier
            .layoutId(layoutId.id)
            .fillMaxWidth(0.8f)
            .clip(RoundedCornerShape(40.dp))
            .border(
                width = 2.5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(40.dp)
            )
            .then(modifier),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
        ),
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = {
            if (text.isNotBlank())
                Icon(
                    modifier = Modifier
                        .clickable { onSendClicked() }
                        .padding(10.dp)
                        .size(30.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.send_icon),
                    contentDescription = null
                )
        },
        maxLines = 3,
        singleLine = false,
        value = text,
        placeholder = { Text(" Say HI!") },
        onValueChange = onValueChanged
    )
}

@Composable
fun ImageCardMatchedScreen(
    modifier: Modifier = Modifier,
    layoutId: LayoutIdMatchedScreen,
    @DrawableRes drawable: Int
) {
    Image(
        modifier = Modifier
            .width(350.dp)
            .height(500.dp)
            .layoutId(layoutId.id)
            .clip(RoundedCornerShape(35.dp))
            .border(
                width = 2.5.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(35.dp)
            )
            .then(modifier),
        painter = painterResource(id = drawable),
        contentScale = ContentScale.Crop,
        contentDescription = null,
    )
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun ButtonMatchedScreen(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int? = null,
    text: String,
    layoutId: LayoutIdMatchedScreen,
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .layoutId(layoutId.id)
            .clip(RoundedCornerShape(35.dp))
            .then(modifier),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        onClick = onClick
    ) {
        Row {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
            if (icon != null)
                Icon(
                    modifier = Modifier
                        .padding(start = 10.dp)
                        .size(20.dp),
                    tint = MaterialTheme.colorScheme.secondaryContainer,
                    painter = painterResource(id = icon),
                    contentDescription = null
                )
        }
    }
}

sealed class LayoutIdMatchedScreen(
    val id: String
) {
    object Container : LayoutIdMatchedScreen("box")
    object Label : LayoutIdMatchedScreen("label")
    object ImageCard1 : LayoutIdMatchedScreen("image1")
    object ImageCard2 : LayoutIdMatchedScreen("image2")
    object TextField : LayoutIdMatchedScreen("message_text_field")
    object BackButton : LayoutIdMatchedScreen("back_button")
    object ShareButton : LayoutIdMatchedScreen("share_button")
    object Logo : LayoutIdMatchedScreen("logo")
}

//@Preview
//@Composable
//private fun b() {
//    AnnyoTheme {
//
//        AnimatedMatchScreen(isMatched = true)
//
//    }
//}
