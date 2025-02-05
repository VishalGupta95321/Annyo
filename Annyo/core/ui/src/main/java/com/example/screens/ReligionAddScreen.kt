package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.ui.BaseInputScreen
import com.example.ui.ChipsGroup
import com.example.ui.ReligionChoices


@Composable
fun ReligionAddScreen(
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
                choices = ReligionChoices() ,
                selectedOptionOrOptions = listOf(selectedOption),
                onSelect = onSelect
            )
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}