package com.example.onboarding_presentation.secondary_screens.sexuality

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.SexualityAddScreen

@Composable
fun SexualityScreen(
    viewModel: SexualityViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val sexualityState by viewModel
        .sexualityState
        .collectAsState()

    val sexualityUpdateState by viewModel
        .sexualityUpdateState
        .collectAsState()

    when (sexualityUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    SexualityAddScreen(
        selectedOption = sexualityState.sexuality.choice,
        onSelect = {
            viewModel.updateSexuality(
                sexualityState.sexuality
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) { onNextCLick() }

    BackHandler { onBackClick() }
}

//BaseInputScreen(
//isNextButtonEnabled = sexualityState.sexuality.choice.isNotEmpty(),
//label1 = "What's Your Sexual Orientation ?",
//inputContent = {
//    ChipsGroup(
//        choices = SexualityChoices() ,
//        selectedOptionOrOptions = listOf(sexualityState.sexuality.choice),
//        onSelect = {
//            viewModel.updateSexuality(
//                sexualityState.sexuality
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }
