package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.*

@Composable
fun InterestsAddScreen(
    addTextFieldValue: String,
    selectedOptions: List<String>,
    onAddTextFieldValueChange: (String) -> Unit,
    onAddButtonCLick: () -> Unit,
    onSelect: (String) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: ()-> Unit,
    onDoneClick: ()-> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            Column {
                Row (
                    modifier = Modifier
                        .padding( horizontal = 10.dp, vertical = 20.dp)
                ){
                    StandardTextField(
                        modifier = Modifier.weight(7.5f),
                        value = addTextFieldValue ,
                        onValueChange = onAddTextFieldValueChange,
                        placeholderText = " Add interests"
                    )
                    StandardButton(
                        modifier = Modifier.padding(5.dp).weight(2.5f),
                        onClick = onAddButtonCLick
                    ) {
                        Text(text = "Add")
                    }
                }

                ChipsGroup(
                    choices = InterestsChoices(selectedOptions) ,
                    selectedOptionOrOptions = selectedOptions,
                    onSelect = onSelect
                )
            }
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}