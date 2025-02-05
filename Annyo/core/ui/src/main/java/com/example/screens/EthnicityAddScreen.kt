package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.ui.BaseInputScreen
import com.example.ui.ChipsGroup
import com.example.ui.EthnicityChoices

@Composable
fun EthnicityAddScreen(
    selectedOption: String,
    onSelect: (String) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: ()-> Unit,
    onDoneClick: ()-> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            ChipsGroup(
                choices = EthnicityChoices() ,
                selectedOptionOrOptions = listOf(selectedOption),
                onSelect = onSelect
            )
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}