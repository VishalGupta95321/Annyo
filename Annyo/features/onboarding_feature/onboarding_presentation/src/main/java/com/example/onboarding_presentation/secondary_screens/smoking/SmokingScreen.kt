package com.example.onboarding_presentation.secondary_screens.smoking

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.SmokingAddScreen

@Composable
fun SmokingScreen(
    viewModel: SmokingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val smokingState by viewModel
        .smokingState
        .collectAsState()

    val smokingUpdateState by viewModel
        .smokingUpdateState
        .collectAsState()

    when (smokingUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    SmokingAddScreen(
        selectedOption = smokingState.smoking.choice,
        onSelect = {
            viewModel.updateSmoking(
                smokingState.smoking
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) { onNextCLick() }

    BackHandler { onBackClick() }
}


//BaseInputScreen(
//isNextButtonEnabled = smokingState.smoking.choice.isNotEmpty(),
//label1 = "Do You Smoke ?",
//inputContent = {
//    ChipsGroup(
//        choices = SmokingChoices() ,
//        selectedOptionOrOptions = listOf(smokingState.smoking.choice),
//        onSelect = {
//            viewModel.updateSmoking(
//                smokingState.smoking
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }
