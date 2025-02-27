package com.app.ggg
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.auth_presentation.R
import kotlinx.coroutines.launch

    @Composable
    fun MatchScreen() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(
                    listOf(
                        Color(0xfff68084),
                        Color(0xffa6c0fe),
                    )
                ))
                .systemBarsPadding()
        ) {
            Box {
                val states = profiles.reversed()
                    .map { it to rememberSwipeableCardState() }
                var hint by remember {
                    mutableStateOf("Swipe a card or press a button below")
                }

                Hint(hint)

                val scope = rememberCoroutineScope()
                Box(Modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .aspectRatio(1f)
                    .align(Alignment.Center)) {
                    states.forEach { (matchProfile, state) ->
                        if (state.swipedDirection == null) {
                            ProfileCard(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .swipableCard(
                                        state = state,
                                        blockedDirections = listOf(Direction.Down),
                                        onSwiped = {
                                            // swipes are handled by the LaunchedEffect
                                            // so that we track button clicks & swipes
                                            // from the same place
                                        },
                                        onSwipeCancel = {
                                            Log.d("Swipeable-Card", "Cancelled swipe")
                                            hint = "You canceled the swipe"
                                        }
                                    ),
                                matchProfile = matchProfile
                            )
                        }
                        LaunchedEffect(matchProfile, state.swipedDirection) {
                            if (state.swipedDirection != null) {
                                hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
                            }
                        }
                    }
                }
                Row(Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CircleButton(
                        onClick = {
                            scope.launch {
                                val last = states.reversed()
                                    .firstOrNull {
                                        it.second.offset.value == Offset(0f, 0f)
                                    }?.second
                                last?.swipe(Direction.Left)
                            }
                        },
                        icon = Icons.Rounded.Close
                    )
                    CircleButton(
                        onClick = {
                            scope.launch {
                                val last = states.reversed()
                                    .firstOrNull {
                                        it.second.offset.value == Offset(0f, 0f)
                                    }?.second

                                last?.swipe(Direction.Right)
                            }
                        },
                        icon = Icons.Rounded.Favorite
                    )
                }
            }
        }
    }



    @Composable
    private fun CircleButton(
        onClick: () -> Unit,
        icon: ImageVector,
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .size(56.dp)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            onClick = onClick
        ) {
            Icon(icon, null,
                tint = MaterialTheme.colorScheme.onPrimary)
        }
    }

    @Composable
    private fun ProfileCard(
        modifier: Modifier,
        matchProfile: MatchProfile,
    ) {
        Card(modifier) {
            Box {
                Image(contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(matchProfile.drawableResId),
                    contentDescription = null)
                Scrim(Modifier.align(Alignment.BottomCenter))
                Column(Modifier.align(Alignment.BottomStart)) {
                    Text(text = matchProfile.name,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(10.dp))
                }
            }
        }
    }

    @Composable
    private fun Hint(text: String) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        }
    }

//    @Composable
//    private fun TransparentSystemBars() {
//        val systemUiController = rememberSystemUiController()
//        val useDarkIcons = false
//
//        DisposableEffect(systemUiController, useDarkIcons) {
//            systemUiController.setSystemBarsColor(
//                color = Color.Transparent,
//                darkIcons = useDarkIcons,
//                isNavigationBarContrastEnforced = false
//            )
//            onDispose {}
//        }
//    }

    private fun stringFrom(direction: Direction): String {
        return when (direction) {
            Direction.Left -> "Left 👈"
            Direction.Right -> "Right 👉"
            Direction.Up -> "Up 👆"
            Direction.Down -> "Down 👇"
        }
    }


data class MatchProfile(
    val name: String,
    @DrawableRes val drawableResId: Int,
)

val profiles = listOf(
    MatchProfile("Erlich Bachman", R.drawable.phone_icon),
    MatchProfile("Richard Hendricks", R.drawable.google_logo),
    MatchProfile("Erlich Bachman", R.drawable.phone_icon),
    MatchProfile("Richard Hendricks", R.drawable.google_logo),
    MatchProfile("Erlich Bachman", R.drawable.phone_icon),
    MatchProfile("Richard Hendricks", R.drawable.google_logo),
)

@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(modifier
        .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
        .height(180.dp)
        .fillMaxWidth())
}

@Preview(showBackground = true)
@Composable
fun p7() {

    MatchScreen()
}
