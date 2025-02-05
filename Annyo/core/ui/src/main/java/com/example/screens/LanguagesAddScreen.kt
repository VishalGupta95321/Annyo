package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.BaseInputScreen
import com.example.ui.ChipsGroup
import com.example.ui.LanguagesChoices
import com.example.ui.StandardTextField

@Composable
fun LanguagesAddScreen(
    searchQuery: String?,
    onSearchQueryChange: (String) -> Unit,
    selectedOptions: List<String>,
    onSelect: (String) -> Unit,
    isNextButtonEnabled: Boolean = true,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        inputContent = {
            Column {
                StandardTextField(
                    modifier = Modifier
                        .padding(
                            horizontal = 10.dp, vertical = 10.dp
                        )
                        .fillMaxWidth(0.8f),
                    value = searchQuery ?: "",
                    onValueChange = onSearchQueryChange,
                    placeholderText = "Search language"
                )
                ChipsGroup(
                    choices = LanguagesChoices(searchQuery),
                    selectedOptionOrOptions = selectedOptions,
                    onSelect = onSelect
                )
            }
        },
        onBackClick = onBackClick
    ) { onDoneClick() }
    BackHandler{onBackClick()}
}