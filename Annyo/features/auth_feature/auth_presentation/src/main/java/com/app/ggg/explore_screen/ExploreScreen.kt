package com.app.ggg.explore_screen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.app.ggg.item
import kotlinx.coroutines.delay
import com.app.auth_presentation.R
import com.app.ggg.quiz_screen.QuizScreen

@OptIn(ExperimentalAnimationApi::class)
@SuppressLint("SuspiciousIndentation")
@Composable
fun ExploreScreen(
    modifier: Modifier = Modifier,
    onMatched: () -> Unit
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var isMatched by remember {
        mutableStateOf(false)
    }
    val listtt = listOf(
        item(
            "No",
            R.drawable.drinking_icon
        ),
        item(
            "Sometimes",
            R.drawable.smoking_icon
        ),
        item(
            "Dog",
            R.drawable.pet_icon
        ),
        item(
            "Nwefew ewo",
            R.drawable.drinking_icon
        ),
        item(
            "Sometimes",
            R.drawable.smoking_icon
        ),
        item(
            "Dofeg",
            R.drawable.pet_icon
        )
    )

    LaunchedEffect(true) {
        delay(1000)
        isMatched = !isMatched
        onMatched()
        delay(3000)
        isMatched = !isMatched
        onMatched()
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState())
        ) {

            SwipableProfilesCards(
                modifier = Modifier.padding(vertical = 5.dp),
                onSwiped = {},
                isFullSized = !isExpanded
            )

            if (isExpanded) {
                NameCard(
                    modifier = Modifier
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            ), shape = RoundedCornerShape(15.dp)
                        )
                        .height(70.dp)

                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.secondaryContainer
                                )
                            ), shape = RoundedCornerShape(15.dp)
                        ),
                ) {
                    repeat(3) {
                        InfoCardItem(
                            text = "Vancouver,Canada",
                            icon = R.drawable.location_filled_icon
                        )
                    }
                }

                FlowRowInfoCard(items = listtt)

                AboutMeCard("About Me")

                FlowRowInfoCard(items = listtt, title = "Lifestyle")

                FlowRowInfoCard(items = listtt, title = "Hobbies")
            }
        }
        ButtonDock(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(
                    brush = Brush.verticalGradient(
                        startY = 0f,
                        endY = 150f,
                        colors = listOf(
                            Color.Transparent,
                            if (isExpanded)
                                MaterialTheme.colorScheme.tertiary
                            else Color.Transparent
                        )
                    ),
                )
        )
        //AnimatedMatchScreen(isMatched)
        AnimatedVisibility(
            visible = isMatched,
            enter = slideIn { IntOffset(-it.width * 0.5.toInt(), -it.height) } + fadeIn(),
            exit = slideOut { IntOffset(-it.width * 0.5.toInt(), -it.height) } + fadeOut()
        ) { QuizScreen() }
    }
}


