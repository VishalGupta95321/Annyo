package com.app.ggg.explore_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R
import com.app.ggg.explore_screen.component.swipe_modifier.rememberSwipeState
import com.app.ggg.explore_screen.component.swipe_modifier.swipeCard


@Composable
fun SwipableProfilesCards(
    modifier: Modifier,
    onSwiped: (String) -> Unit,
    isFullSized: Boolean
) {
    val cardHeight by animateFloatAsState(
        targetValue = if (isFullSized) 600f else 450f,
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
        contentAlignment = Alignment.TopCenter
    ) {
        repeat(4) {
            var isSwiped by remember {
                mutableStateOf<String?>(null)
            }
            if (isSwiped == null) {
                Box(
                    modifier = Modifier
                        .swipeCard(state = rememberSwipeState(),
                            horizontalSwipeEnabled = true,
                            verticalSwipeEnabled = true,
                            onSwipedLeft = {
                                onSwiped("")
                                isSwiped = ""
                                println(" swiped left")
                            },
                            onSwipedRight = {
                                onSwiped("")
                                isSwiped = ""
                            })
                        .width(375.dp)
                        .height(cardHeight.dp)
                        .clip(RoundedCornerShape(30.dp))
                        .background(Color.DarkGray)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.jon_ly_uipjy2xrojq_unsplash),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(10.dp)
                    ) {
                        NameCard(
                            modifier = Modifier.background(
                                color = Color.Transparent,
                            ),
                            style = MaterialTheme.typography.headlineMedium.copy(
                                shadow = Shadow(
                                    color = Color(0x1E312D2D),
                                    offset = Offset(0f, 5f),
                                    blurRadius = 5f
                                ),
                                color = Color.White
                            )
                        )
                        InfoCardItem(
                            text = "Vancouver,Canada",
                            iconColor = MaterialTheme.colorScheme.primary,
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                shadow = Shadow(
                                    color = Color(0x1E312D2D),
                                    offset = Offset(0f, 5f),
                                    blurRadius = 5f
                                ),
                                color = Color.White
                            ),
                            icon = R.drawable.location_filled_icon
                        )
                        InfoCardItem(
                            text = "Harvard University",
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                shadow = Shadow(
                                    color = Color(0x1E312D2D),
                                    offset = Offset(0f, 5f),
                                    blurRadius = 5f
                                ),
                                color = Color.White
                            ),
                            icon = R.drawable.education_icon
                        )
                    }
                }
            }
        }
    }
}
