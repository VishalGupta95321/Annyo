package com.example.profile_presentation.primary_screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.profile_presentation.components.addAnimatedProgressIndicator
import com.example.ui.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            ProfileHeader(profileCompletedPercentage = 90F) {

            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .weight(1f)
        ) {
            ProfileScreenMenuItem(text = "hi", logo = R.drawable.search) {}
            ProfileScreenMenuItem(text = "hi", logo = R.drawable.search) {}
            ProfileScreenMenuItem(text = "hi", logo = R.drawable.search) {}
            ProfileScreenMenuItem(text = "hi", logo = R.drawable.search) {}
        }
    }
}


@Composable
fun ProfileHeader(
    modifier: Modifier = Modifier,
    profileCompletedPercentage: Float,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .clickable(
                    indication = null, interactionSource =
                    remember { MutableInteractionSource() }
                ) { onClick() }
                .addAnimatedProgressIndicator(
                    indicatorColor =
                    MaterialTheme.colorScheme.primary,
                    percentageCompleted =
                    profileCompletedPercentage,
                )
                .padding(10.dp)
                .size(210.dp)
                .clip(CircleShape)
                .then(modifier),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                model = R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash,
                contentScale = ContentScale.Crop
            ),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Taylor Brooks",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall,
        )
    }

}


@Composable
fun ProfileScreenMenuItem(
    text: String,
    @DrawableRes logo: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(
                indication = null, interactionSource =
                remember { MutableInteractionSource() }
            ) { onClick() }
            .padding(5.dp)
            .fillMaxWidth(0.95f)
            .height(60.dp)
            .clip(RoundedCornerShape(40.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        elevation = CardDefaults.elevatedCardElevation(),
        shape = RoundedCornerShape(40.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 30.dp, vertical = 10.dp),
                color = MaterialTheme.colorScheme.primary,
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )
            Icon(
                modifier = Modifier
                    .size(90.dp)
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .align(Alignment.CenterEnd),
                painter = painterResource(id = logo),
                contentDescription = null
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PPPPPPP() {
    ProfileScreen()
}

@Preview(
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=480,orientation=portrait"
)
@Composable
fun PPPPPPPP() {
    ProfileScreen()
}

//@Composable
//fun ProfileScreenCompleteProfileCard(
//    modifier: Modifier  = Modifier
//){
//    val colors = listOf(
//        MaterialTheme.colorScheme.primary,
//        MaterialTheme.colorScheme.primaryContainer
//    )
//
//    var targetRotation by remember {
//        mutableStateOf(0f)
//    }
//
//    val rotationX by animateFloatAsState(
//        targetValue = targetRotation,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioHighBouncy,
//            stiffness = Spring.StiffnessMediumLow
//        )
//    )
//
//    val rotationY by animateFloatAsState(
//        targetValue = targetRotation,
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioHighBouncy,
//            stiffness = Spring.StiffnessMediumLow
//        )
//    )
//
//    LaunchedEffect(true){
//        delay(1500)
//        targetRotation = 4f
//        delay(200)
//        targetRotation = 0f
//    }
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier
//            .graphicsLayer {
//                this.rotationX = rotationX
//                this.rotationY = -rotationY
//            }
//            .fillMaxWidth()
//            .padding(10.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(
//                brush = Brush.linearGradient(
//                    colors = colors
//                )
//            )
//            .then(modifier)
//    ) {
//        Button(
//            modifier = Modifier
//                .padding(top = 5.dp)
//                .clip(RoundedCornerShape(35.dp)),
//            colors = ButtonDefaults.buttonColors(
//                containerColor = MaterialTheme.colorScheme.primary,
//                contentColor = MaterialTheme.colorScheme.primaryContainer
//            ),
//            onClick = {}
//        ) {
//            Text(
//                text = "Complete Profile",
//                style = MaterialTheme.typography.titleMedium
//            )
//        }
//        Text(
//            color = Color.White,
//            text = "Go Live",
//            style = MaterialTheme.typography.titleLarge.copy(
//                // fontFamily = FontFamily(Font(R.font))
//            )
//        )
//    }
//}


//@Composable
//fun ProfileHeaderWithProgressIndicator (
//    modifier: Modifier = Modifier,
//    overallIndicatorValue: Float = 100f,
//    //  filledIndicatorValue: Float = 100f
//) {
//
//    var filledIndicatorValue by remember {
//        mutableStateOf(0f)
//    }
//    val percentage = (filledIndicatorValue / overallIndicatorValue) * 100
//
//    val sweepAngle by animateFloatAsState(
//        targetValue = (2.4 * percentage).toFloat(),
//        animationSpec = spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness = Spring.StiffnessLow
//        )
//    )
//
//    LaunchedEffect(true){
//        delay(1000)
//        filledIndicatorValue = 70f
//    }
//
//
//    val color = MaterialTheme.colorScheme.primary
//
//    Image(
//        modifier = Modifier
//            .drawBehind {
//                progressIndicator(
//                    Color.LightGray,
//                    30f
//                )
//                progressIndicator(
//                    color,
//                    30f,
//                    sweepAngle
//                )
//            }
//            .size(500.dp)
//            .padding(9.dp)
//            .clip(CircleShape)
//            .then(modifier),
//        contentScale = ContentScale.Crop,
//        painter = painterResource(id = com.example.ui.R.drawable.ayo_ogunseinde_hevq8p4eqlg_unsplash) ,
//        contentDescription = null
//    )
//}
