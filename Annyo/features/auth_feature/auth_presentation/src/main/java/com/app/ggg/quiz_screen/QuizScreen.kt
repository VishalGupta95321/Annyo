package com.app.ggg.quiz_screen

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R
import kotlinx.coroutines.delay

@Composable
fun QuizScreen() {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.scrim
    )

    var  selected by remember {
        mutableStateOf(0)
    }

    var animate by remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = colors,
                )
            )
            .padding(15.dp)
    ){
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            repeat(4){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }) { }
                            .padding(13.dp)
                            .size(
                                if (selected == it + 2) 35.dp else 25.dp
                            ),
                        tint =  if (selected==it+2) Color.White else MaterialTheme.colorScheme.tertiary,
                        painter = painterResource(id = R.drawable.ques_icon),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier,
                        color = if (selected==it+2) Color.White else MaterialTheme.colorScheme.tertiary,
                        text = (it+1).toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }

//      QuizScreenCard(
//          animate = animate,
//          selected = selected==1,
//          card = 0,
//          modifier = Modifier.height(110.dp),
//          text = "LKMCACDS",
////          onCLick = {clicked = 1},
////          clicked = clicked
//      ){
//          animate  =!animate
//          selected = 1
//      }
        Text(
            modifier = Modifier
                .padding(7.dp)
                .animateQuizScreenCard(
                    animate,
                    1

                )
                .heightIn(50.dp, 100.dp),
            text = "Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name Whats my Name  ",
            color = MaterialTheme.colorScheme.primaryContainer,
            style = MaterialTheme.typography.labelLarge
                .copy(fontFamily = FontFamily(Font(R.font.aclonica))),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(30.dp))
        repeat(4){
            QuizScreenCard(
                animate = animate,
                selected = selected==it+2,
                card = it+1,
                modifier = Modifier.height(80.dp),
                text = "Process safety management compliance is essential for today's process facilities. Every facility strives for a safe operation ",
//                onCLick = {clicked = it+2},
//                clicked = clicked
            ){
                selected = it+2
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .clip(RoundedCornerShape(35.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {animate = !animate}
            ){
                Text(
                    modifier = Modifier,
                    text = "Next",
                    style = MaterialTheme.typography.titleLarge
                )
            }}
    }
    println(("Process safety management compliance is essential for today's process facilities. Every facility strives for a safe operation").length)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreenCard(
    selected:Boolean,
    animate:Boolean,
   // clicked:Boolean,
    card:Int,
    modifier: Modifier = Modifier,
    text: String,
   /// onCLick: () -> Unit,
    onSelect: ()->Unit,
) {

    val scaleX by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        tween(300)
    )
    val scaleY by animateFloatAsState(
        targetValue = if (selected) 1.2f else 1f ,
        tween(300)
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .animateQuizScreenCard(card = card, selected = animate)
            .clickable {
                onSelect()
                //  onCLick()
            }
            .fillMaxWidth(0.95f)
            .graphicsLayer {
                this.scaleX = scaleX
                this.scaleY = scaleY
            }
            .padding(vertical = 10.dp)
            .background(
                brush = if (selected)
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                else Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.primaryContainer
                    )
                ),
                shape = RoundedCornerShape(
                    30.dp
                )
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.tertiary,
                shape = RoundedCornerShape(30.dp)
            )

            .then(modifier)
    ) {
        Text(
            modifier = Modifier.padding(7.dp),
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.Center
        )
    }
}

fun Modifier.animateQuizScreenCard(
    selected:Boolean,
    card:Int,
):Modifier  = composed {

    val rotationY by remember {
        mutableStateOf(Animatable(0f))
    }

   // if (startAnimation)
        LaunchedEffect(selected){
            when(card){
                0 -> {delay(100)}
                1 -> {delay(150)}
                2 -> {delay(200)}
                3 -> {delay(250)}
                4 -> {delay(300)}
            }
            rotationY.animateTo(
                targetValue = 180f,
                animationSpec = tween(
                    durationMillis = 1500,
                )
            )
            rotationY.snapTo(
                targetValue = 0f,
            )
//            rotationY.animateTo(
//                targetValue = 180f,
//                animationSpec = tween(
//                    durationMillis = 2000,
//                )
//            )
//            rotationY.snapTo(
//                targetValue = 0f,
//            )
        }

    graphicsLayer {
        this.rotationY = rotationY.value
        cameraDistance = 12 * density
    }
}

//@Preview
//@Composable
//fun p16() {
//    QuizScreen()
//}