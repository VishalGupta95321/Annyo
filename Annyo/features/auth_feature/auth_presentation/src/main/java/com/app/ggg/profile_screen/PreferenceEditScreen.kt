package com.app.ggg.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.app.auth_presentation.R

@Composable
fun PreferenceEditScreen(

) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
    ) {

        Text(
            modifier = Modifier
                .padding(vertical = 25.dp),
            color = MaterialTheme.colorScheme.primary,
            text = "Basic Preferences",
            style = MaterialTheme.typography.headlineSmall
        )
        PreferenceEditScreenCard()

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(400.dp)
//        ) {
            PreferenceEditScreenSliderCard(
                name = "Maximum Distance",
                indicatingValues = "100 miles",
                sliderState = SliderState.Single(
                    50f,
                    0f..100f
                )
            )
            PreferenceEditScreenSliderCard(
                name = "Maximum Age",
                indicatingValues = "18 to 22",
                sliderState = SliderState.Range(
                    1f..50f,
                    0f..100f
                )
            )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 30.dp),
        ) {
            Text(
                modifier = Modifier
                    .weight(1.5f),
                color = MaterialTheme.colorScheme.primary,
                text = "Plus Preferences",
                style = MaterialTheme.typography.titleLarge
            )
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(35.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.primaryContainer
                ),
                onClick = {}
            ) {
                Text(
                    modifier = Modifier
                        .padding(5.dp),
                    text = "Upgrade",
                    style = MaterialTheme.typography.titleMedium
                )
                Icon(
                    painter = painterResource(id = R.drawable.membership_icon),
                    contentDescription = null
                )
            }
        }

            PreferenceEditScreenSliderCard(
                name = "Maximum Height",
                indicatingValues = "5'5\" to 6'0\"",
                sliderState = SliderState.Range(
                    1f..50f,
                    0f..100f
                )
            )
      //  }
    }
}

@Composable
fun PreferenceEditScreenCard(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(vertical = 5.dp)
            .height(60.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(30.dp)
            )
            .then(modifier)
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            text = "Looking For",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            modifier = Modifier
                .padding(5.dp),
            text = "Women",
            style = MaterialTheme.typography.labelLarge
        )
        Icon(
            modifier = Modifier
                .padding(5.dp),
            painter = painterResource(id = R.drawable.arrow_right_icon),
            contentDescription = null
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferenceEditScreenSliderCard(
    name:String,
    indicatingValues: String,
    modifier: Modifier = Modifier,
    sliderState: SliderState
) {
    Card(
        modifier = Modifier
            .height(125.dp)
            .padding(vertical = 5.dp)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ),
        shape = RoundedCornerShape(30.dp)
    ){
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(2.dp),
            text = name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge
        )
        when(sliderState){
            is SliderState.Range -> {
                RangeSlider(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    colors = SliderDefaults.colors(
                        inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                    ),
                    value = sliderState.value,
                    valueRange =sliderState.valueRange,
                    onValueChange = {}
                )
            }
           is SliderState.Single -> {
               Slider(
                   modifier = Modifier
                       .align(Alignment.CenterHorizontally),
                   colors = SliderDefaults.colors(
                       inactiveTrackColor = MaterialTheme.colorScheme.primaryContainer
                   ),
                   value = sliderState.value,
                   valueRange =sliderState.valueRange,
                   onValueChange = {}
               )
           }
            else -> {}
        }
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(2.dp),
            text = indicatingValues,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

sealed interface SliderState{
    data class Single(
        val value : Float = 0f,
        val valueRange : ClosedFloatingPointRange<Float> = 1f..10f
    ): SliderState
    data class Range(
        val value : ClosedFloatingPointRange<Float> = 0f..1f,
        val valueRange : ClosedFloatingPointRange<Float> = 1f..10f
    ): SliderState
}