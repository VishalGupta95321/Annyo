package com.example.onboarding_presentation.secondary_screens.drinking

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.DrinkingAddScreen

@Composable
fun DrinkingScreen(
    viewModel: DrinkingViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val drinkingState by viewModel
        .drinkingState
        .collectAsState()

    val drinkingUpdateState by viewModel
        .drinkingUpdateState
        .collectAsState()

    when (drinkingUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }


    DrinkingAddScreen(
        selectedOption = drinkingState.drinking.choice,
        onSelect = {
            viewModel.updateDrinking(
                drinkingState.drinking
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}

    BackHandler { onBackClick() }
}


//BaseInputScreen(
//isNextButtonEnabled = drinkingState.drinking.choice.isNotEmpty(),
//label1 = "Do You Drink ?",
//inputContent = {
//    ChipsGroup(
//        choices = DrinkingChoices() ,
//        selectedOptionOrOptions = listOf(drinkingState.drinking.choice),
//        onSelect = {
//            viewModel.updateDrinking(
//                drinkingState.drinking
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }
