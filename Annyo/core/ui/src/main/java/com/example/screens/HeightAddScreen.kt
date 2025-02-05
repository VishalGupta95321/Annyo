package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.BaseInputScreen
import com.example.ui.DropDownList
import com.example.ui.HeightOptions

@Composable
fun HeightAddScreen(
    heightInFeet: String,
    onSelect: (String) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: ()-> Unit,
    onDoneClick: ()-> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            Column(
                Modifier.fillMaxWidth(0.95f)
                    .padding(top = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DropDownList(
                    modifier = Modifier.width(150.dp),
                    title = if (heightInFeet=="")
                        "Height"
                    else heightInFeet,
                    options = HeightOptions() ,
                    listHeight = 500.dp,
                    onSelect = onSelect
                )
            }
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}