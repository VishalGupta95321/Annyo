package com.app.ggg.profile_screen

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.auth_presentatio.progressIndicator
import com.app.auth_presentation.R
import kotlinx.coroutines.delay

//@Composable
//fun ProfileScreen() {
//    val list = listOf(
//        CardItem(
//            "Membership",
//            R.drawable.membership_icon
//        ),
//        CardItem(
//            "Preferences",
//            R.drawable.prefrences
//        ),
//        CardItem(
//            "Edit Profile",
//            R.drawable.edit
//        ),
//        CardItem(
//            "Manage Account",
//            R.drawable.setting_icon
//        ),
//    )
//    Column(
//        verticalArrangement = Arrangement.Bottom,
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            modifier = Modifier
//                .padding(10.dp)
//                .weight(1f)
//                .fillMaxWidth()
//        ){
//            ProfileScreenProfilePicWithIndicator()
//            Text(
//                modifier = Modifier
//                    .padding(10.dp),
//                text = "Taylor Brooks",
//                color = MaterialTheme.colorScheme.primary,
//                style = MaterialTheme.typography.headlineSmall,
//            )
//            ProfileScreenCompleteProfileCard(
//                modifier = Modifier.weight(0.4f)
//            )
//        }
//        Column(
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//            list.forEach { item ->
//                ProfileScreenItem(
//                    text = item.name,
//                    logo = item.logo
//                )
//            }
//        }
//    }
//}
//
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
//                fontFamily = FontFamily(Font(R.font.aclonica))
//            )
//        )
//    }
//}
//
//@Composable
//fun ProfileScreenProfilePicWithIndicator (
//    modifier: Modifier = Modifier,
//    overallIndicatorValue: Float = 100f,
//  //  filledIndicatorValue: Float = 100f
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
//           dampingRatio = Spring.DampingRatioLowBouncy,
//           stiffness = Spring.StiffnessLow
//      )
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
//            .size(180.dp)
//            .padding(9.dp)
//            .clip(CircleShape)
//            .then(modifier),
//        contentScale = ContentScale.Crop,
//        painter = painterResource(id = R.drawable.jon_ly_uipjy2xrojq_unsplash) ,
//        contentDescription = null
//    )
//}
//
//
//@Composable
//fun ProfileScreenItem(
//    text: String,
//    @DrawableRes logo: Int
//) {
//    Card(
//        modifier = Modifier
//            .padding(5.dp)
//            .fillMaxWidth()
//            .height(55.dp)
//            .clip(RoundedCornerShape(40.dp)),
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            contentColor = MaterialTheme.colorScheme.primary
//        ),
//        elevation = CardDefaults.elevatedCardElevation(
//            defaultElevation = 10.dp
//        ),
//        shape = RoundedCornerShape(40.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//        ) {
//            Text(
//                modifier = Modifier
//                    .align(Alignment.CenterStart)
//                    .padding(horizontal = 30.dp, vertical = 10.dp),
//                color = MaterialTheme.colorScheme.primary,
//                text = text,
//                style = MaterialTheme.typography.bodyMedium,
//                fontSize = 18.sp
//            )
//            Icon(
//                modifier = Modifier
//                    .size(90.dp)
//                    .padding(horizontal = 30.dp, vertical = 10.dp)
//                    .align(Alignment.CenterEnd),
//                painter = painterResource(id = logo),
//                contentDescription = null
//            )
//        }
//    }
//}
