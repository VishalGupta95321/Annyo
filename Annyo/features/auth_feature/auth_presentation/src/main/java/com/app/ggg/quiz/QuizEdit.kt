package com.app.ggg.quiz

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.auth_presentation.R
import kotlinx.coroutines.delay

@Composable
fun EditQuizScreen() {

    var text by remember {
        mutableStateOf("")
    }

    var selectedAnswer by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Question",
            style = MaterialTheme.typography.titleLarge
        )
        EditQuizScreenTextFieldWithLengthCount(
            text = "",
            placeHolderText = " Question",
            trailingIcon = R.drawable.ques_icon,
            onSelectAnswer = {}
        ){}
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Options",
            style = MaterialTheme.typography.titleLarge
        )
        Column {
            repeat(4){ it ->
                EditQuizScreenTextFieldWithLengthCount(
                    animateError = it==1,
                    text = "",
                    maxLines = 2,
                    placeHolderText = " Option ${it+1}",
                    trailingIcon = R.drawable.check_icon,
                    isSelected = selectedAnswer==it+1,
                    onSelectAnswer = {selectedAnswer = it+1}
                ){}
            }
        }
     }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditQuizScreenTextFieldWithLengthCount(
    modifier: Modifier = Modifier,
    animateError:Boolean = false,
    maxLines : Int = 3,
    placeHolderText: String,
    text: String,
    @DrawableRes trailingIcon:Int,
    isSelected:Boolean = true,
    maxLength: Int = 200,
    onSelectAnswer: ()->Unit,
    onValueChanged: (String) -> Unit,
) {

    val tintColor by animateColorAsState(
        targetValue = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.tertiary,
        animationSpec = tween(
            durationMillis = 300,
            easing = LinearOutSlowInEasing
        )
    )
    Column(
        modifier = modifier
        .animateQuizTextField(animateError)
    ){
        TextField( // todo maybe replace with standard texxt field
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                ),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            textStyle = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.secondary,
            ),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ) { onSelectAnswer() }
                        .padding(13.dp)
                        .size(30.dp),
                    tint = tintColor,
                    painter = painterResource(id = trailingIcon),
                    contentDescription = null
                )
            },
            maxLines = maxLines,
            singleLine = false,
            value = text,
            placeholder = { Text(placeHolderText) },
            onValueChange = onValueChanged
        )

        Text(
            buildAnnotatedString {
              withStyle(
                  style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = if(text.length>maxLength)
                        Color.Red
                    else
                        Color.Black
                )) {
                append("${text.length}")
                withStyle(style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )) {
                    append("/${maxLength}")
                }
            }
        }, modifier = Modifier
                .align(Alignment.End)
                .padding(end = 7.dp)
        )
    }
}

fun Modifier.animateQuizTextField(
    startAnimation:Boolean = false,
):Modifier  = composed {

    var targetValue by remember {
        mutableStateOf(0f)
    }
    val translationX by animateFloatAsState(
        targetValue = targetValue,
        animationSpec = SpringSpec(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        )
    )

    if (startAnimation)
        LaunchedEffect(true ){
            targetValue = -50f
            delay(200)
            targetValue = +50f
            delay(200)
            targetValue = 0f
        }

    graphicsLayer {
        this.translationX = translationX
    }
}
