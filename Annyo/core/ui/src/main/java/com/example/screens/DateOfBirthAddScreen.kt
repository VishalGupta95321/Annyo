package com.example.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui.*

@Composable
fun DateOfBirthAddScreen(
    day: Int,
    month: String,
    year: Int,
    isNextButtonEnabled: Boolean = true,
    onDaySelect: (String) -> Unit,
    onMonthSelect: (String) -> Unit,
    onYearSelect: (String) -> Unit,
    onBackClick: ()-> Unit,
    onDoneClick: ()-> Unit,
) {
    BaseInputScreen(
        isNextButtonEnabled = isNextButtonEnabled,
        label1 = "When is your BirthDay ?",
        inputContent = {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 50.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                DropDownList(
                    modifier = Modifier.width(90.dp),
                    title = if (day==0)
                        "day"
                    else day.toString(),
                    listHeight = 500.dp,
                    options = DaysOfMonthOptions(),
                    onSelect = onDaySelect
                )

                DropDownList(
                    modifier = Modifier.width(155.dp),
                    title = month ,
                    listHeight = 500.dp,
                    options = MonthsOptions(),
                    onSelect = onMonthSelect
                )
                DropDownList(
                    modifier = Modifier.width(110.dp),
                    title = if (year==0)
                        "year"
                    else year.toString(),
                    listHeight = 500.dp,
                    options = YearOptions(),
                    onSelect = onYearSelect
                )
            }
        },
        onBackClick = onBackClick
    ) {onDoneClick()}
    BackHandler{onBackClick()}
}