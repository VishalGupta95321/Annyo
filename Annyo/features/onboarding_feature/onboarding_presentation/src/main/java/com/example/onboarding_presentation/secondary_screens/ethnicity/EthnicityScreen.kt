package com.example.onboarding_presentation.secondary_screens.ethnicity

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.EthnicityAddScreen

@Composable
fun EthnicityScreen(
    viewModel: EthnicityViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val ethnicityState by viewModel
        .ethnicityState
        .collectAsState()

    val ethnicityUpdateState by viewModel
        .ethnicityUpdateState
        .collectAsState()

    when (ethnicityUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    EthnicityAddScreen(
        selectedOption = ethnicityState.ethnicity.choice,
        onSelect =  {
            viewModel.updateEthnicity(
                ethnicityState.ethnicity
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}

    BackHandler { onBackClick() }
}

//
//BaseInputScreen(
//isNextButtonEnabled = ethnicityState.ethnicity.choice.isNotEmpty(),
//label1 = "What's Your Ethnicity ?",
//inputContent = {
//    ChipsGroup(
//        choices = EthnicityChoices() ,
//        selectedOptionOrOptions = listOf(ethnicityState.ethnicity.choice),
//        onSelect = {
//            viewModel.updateEthnicity(
//                ethnicityState.ethnicity
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }