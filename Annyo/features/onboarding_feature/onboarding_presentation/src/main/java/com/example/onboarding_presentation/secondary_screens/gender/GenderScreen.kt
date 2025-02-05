package com.example.onboarding_presentation.secondary_screens.gender

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.GenderAddScreen

@Composable
fun GenderScreen(
    viewModel: GenderViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val genderState by viewModel
        .genderState
        .collectAsState()

    val genderUpdateState by viewModel
        .genderUpdateState
        .collectAsState()

    when (genderUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    GenderAddScreen(
        selectedOption = genderState.gender.choice,
        onSelect =  {
            viewModel.updateGender(
                genderState.gender
                    .copy(choice = it)
            )} ,
        onBackClick = onBackClick
    ) {onNextCLick()}

    BackHandler { onBackClick() }
}

//BaseInputScreen(
//isNextButtonEnabled = genderState.gender.choice.isNotEmpty(),
//label1 = "What's Your gender ?",
//inputContent = {
//    ChipsGroup(
//        choices = GenderChoices() ,
//        selectedOptionOrOptions = listOf(genderState.gender.choice),
//        onSelect = {
//            viewModel.updateGender(
//                genderState.gender
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClic