package com.example.onboarding_presentation.secondary_screens.religion

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.ReligionAddScreen

@Composable
fun ReligionScreen(
    viewModel: ReligionViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val religionState by viewModel
        .religionState
        .collectAsState()

    val religionUpdateState by viewModel
        .religionUpdateState
        .collectAsState()

    when (religionUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    ReligionAddScreen(
        selectedOption = religionState.religion.choice,
        onSelect =  {
            viewModel.updateReligion(
                religionState.religion
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}

//BaseInputScreen(
//isNextButtonEnabled = religionState.religion.choice.isNotEmpty(),
//label1 = "Do \nYou follow any Religion ?",
//inputContent = {
//    ChipsGroup(
//        choices = ReligionChoices() ,
//        selectedOptionOrOptions = listOf(religionState.religion.choice),
//        onSelect = {
//            viewModel.updateReligion(
//                religionState.religion
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }