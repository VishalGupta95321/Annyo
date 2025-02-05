package com.app.ggg

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pracoffcet (

){
    val width by remember{ mutableStateOf(200) }
    val height by remember{ mutableStateOf(200) }

    val screenWidth = with(LocalDensity.current) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }
    val screenHeight = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }

Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
    Card(
        modifier = Modifier
            //.offset((screenWidth*0f).dp, (screenHeight * 0f).dp)
            .width(width.dp)
            .graphicsLayer {
                this.translationY = screenHeight*0.5f
                this.translationX = screenWidth*0.5f
            }//.po
            .height(height.dp),
        onClick = { /*TODO*/ }
    ) {

    }
}
}
@Preview
@Composable
fun weevvw(){
    Pracoffcet()
}