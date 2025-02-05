package com.example.onboarding_presentation.secondary_screens.pets

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.PetAddScreen

@Composable
fun PetsScreen(
    viewModel: PetsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val petsState by viewModel
        .petsState
        .collectAsState()

    val petsUpdateState by viewModel
        .petsUpdateState
        .collectAsState()

    when (petsUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }
    PetAddScreen(
        selectedOption = petsState.pets.choice,
        onSelect = {
            viewModel.updatePets(
                petsState.pets
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}
    BackHandler { onBackClick() }
}


//BaseInputScreen(
//isNextButtonEnabled = petsState.pets.choice.isNotEmpty(),
//label1 = "Do You\nHave any Pets ?",
//inputContent = {
//    ChipsGroup(
//        choices = PetsChoices() ,
//        selectedOptionOrOptions = listOf(petsState.pets.choice),
//        onSelect = {
//            viewModel.updatePets(
//                petsState.pets
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }