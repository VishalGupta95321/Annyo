package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.BaseInputScreen
import com.example.ui.StandardTextField

@Composable
fun AboutMeAddScreen(
    text:String,
    onValueChange: (String) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: ()-> Unit,
    onDoneClick: ()-> Unit
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            StandardTextField(
                placeholderText = "About you",
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(0.9f),
                singleLine = false,
                maxLines = 5,
                value = text,
                onValueChange = onValueChange,
            )
        },
        onBackClick = onBackClick
    ) { onDoneClick() }

    BackHandler{onBackClick()}
}