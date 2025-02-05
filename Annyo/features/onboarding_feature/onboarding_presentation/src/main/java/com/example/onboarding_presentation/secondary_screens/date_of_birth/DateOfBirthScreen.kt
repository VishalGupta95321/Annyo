package com.example.onboarding_presentation.secondary_screens.date_of_birth

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.profile.model.DateOfBirth
import com.example.profile.model.DateOfBirth.Companion.monthFromString
import com.example.screens.DateOfBirthAddScreen

@Composable
fun DateOfBirthScreen(
    viewModel: DateOfBirthViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val dateOfBirthState by viewModel
        .dateOfBirthState
        .collectAsState()

    val dateOfBirthUpdateState by viewModel
        .dateOfBirthUpdateState
        .collectAsState()


    DateOfBirthAddScreen(
        isNextButtonEnabled = dateOfBirthUpdateState==ProfileUpdateState.Updated,
        day = dateOfBirthState.dateOfBirth.day,
        month =  DateOfBirth.monthFromInt(
            dateOfBirthState.dateOfBirth.month
        ) ,
        year = dateOfBirthState.dateOfBirth.year,
        onDaySelect = {
            viewModel.onDateOfBirthChange(
                dateOfBirthState.dateOfBirth.copy(
                    day = it.toInt()
                )
            )
        },
        onMonthSelect = {
            viewModel.onDateOfBirthChange(
                dateOfBirthState.dateOfBirth.copy(
                    month = monthFromString(it)
                )
            )
        },
        onYearSelect = {
            viewModel.onDateOfBirthChange(
                dateOfBirthState.dateOfBirth.copy(
                    year = it.toInt()
                )
            )
        },
        onBackClick = onBackClick,
    ) { onNextCLick() }

    BackHandler { onBackClick() }
}

//BaseInputScreen(
//label1 = "When is your BirthDay ?",
//inputContent = {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 50.dp),
//        horizontalArrangement = Arrangement.SpaceAround
//    ) {
//        DropDownList(
//            modifier = Modifier.width(90.dp),
//            title = if (dateOfBirthState.dateOfBirth.day == 0)
//                "day"
//            else dateOfBirthState.dateOfBirth.day.toString(),
//            listHeight = 500.dp,
//            options = DaysOfMonthOptions()
//        ) {
//            viewModel.onDateOfBirthChange(
//                dateOfBirthState.dateOfBirth
//                    .copy(day = it.toInt())
//            )
//        }
//        DropDownList(
//            modifier = Modifier.width(155.dp),
//            title = if (dateOfBirthState.dateOfBirth.month == 0)
//                "Month"
//            else DateOfBirth.monthFromInt(dateOfBirthState.dateOfBirth.month),
//            listHeight = 500.dp,
//            options = MonthsOptions()
//        ) {
//            viewModel.onDateOfBirthChange(
//                dateOfBirthState.dateOfBirth
//                    .copy(month = DateOfBirth.monthFromString(it))
//            )
//        }
//        DropDownList(
//            modifier = Modifier.width(110.dp),
//            title = if (dateOfBirthState.dateOfBirth.year == 0)
//                "year"
//            else dateOfBirthState.dateOfBirth.year.toString(),
//            listHeight = 500.dp,
//            options = YearOptions()
//        ) {
//            viewModel.onDateOfBirthChange(
//                dateOfBirthState.dateOfBirth
//                    .copy(year = it.toInt())
//            )
//        }
//    }
//},
//onBackClick = onBackClick
//) {
//    viewModel.updateDateOfBirth(
//        dateOfBirthState.dateOfBirth
//    )
//}


