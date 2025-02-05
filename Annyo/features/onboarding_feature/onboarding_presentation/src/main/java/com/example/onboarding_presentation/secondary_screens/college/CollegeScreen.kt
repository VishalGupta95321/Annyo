package com.example.onboarding_presentation.secondary_screens.college

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.CollegeAddScreen

@Composable
fun CollegeScreen(
    viewModel: CollegeViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val collegeState by viewModel
        .collegeState
        .collectAsState()

    val aboutUpdateState by viewModel
        .collegeUpdateState
        .collectAsState()

    when (aboutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    CollegeAddScreen(
        collegeName = collegeState.college.collegeName,
        onValueChange =  { value ->
            viewModel.updateCollege(
                collegeState.college
                    .copy(value)
            )
        },
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

//
//BaseInputScreen(
//label1 = "Ok Cool,\nWhat about your College ?",
//inputContent = {
//    StandardTextField(
//        placeholderText = "College name",
//        modifier = Modifier
//            .padding(10.dp)
//            .fillMaxWidth(0.9f),
//        singleLine = false,
//        maxLines = 5,
//        value = collegeState.college.collegeName,
//        onValueChange = { value ->
//            viewModel.updateCollege(
//                collegeState.college
//                    .copy(value)
//            )
//        },
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }