package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.example.ui.BaseInputScreen
import com.example.ui.ChildrenChoices
import com.example.ui.ChipsGroup

@Composable
fun ChildrenAddScreen(
    selectedOption: String,
    isNextButtonEnabled: Boolean = true,
    onBackClick: ()-> Unit,
    onSelect: (String) -> Unit,
    onDoneClick: ()-> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            ChipsGroup(
                choices = ChildrenChoices() ,
                selectedOptionOrOptions = listOf(selectedOption),
                onSelect = onSelect
            )
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}