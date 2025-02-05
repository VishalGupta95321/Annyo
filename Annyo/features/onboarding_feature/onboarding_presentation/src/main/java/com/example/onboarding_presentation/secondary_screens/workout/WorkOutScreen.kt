package com.example.onboarding_presentation.secondary_screens.workout

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.onboarding_presentation.ProfileUpdateState
import com.example.screens.WorkoutAddScreen

@Composable
fun WorkoutScreen(
    viewModel: WorkoutViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onNextCLick: () -> Unit
) {

    val workoutState by viewModel
        .workoutState
        .collectAsState()

    val workoutUpdateState by viewModel
        .workoutUpdateState
        .collectAsState()

    when (workoutUpdateState) {
        ProfileUpdateState.NotUpdating -> {}
        is ProfileUpdateState.UpdateFailed -> {}
        ProfileUpdateState.Updated -> {}
        ProfileUpdateState.Updating -> {}
    }

    WorkoutAddScreen(
        selectedOption = workoutState.workout.choice,
        onSelect = {
            viewModel.updateWorkout(
                workoutState.workout
                    .copy(choice = it)
            )},
        onBackClick = onBackClick
    ) {onNextCLick()}

    BackHandler { onBackClick() }
}


//BaseInputScreen(
//isNextButtonEnabled = workoutState.workout.choice.isNotEmpty(),
//label1 = "Do You WorkOut ?",
//inputContent = {
//    ChipsGroup(
//        choices = WorkoutChoices() ,
//        selectedOptionOrOptions = listOf(workoutState.workout.choice),
//        onSelect = {
//            viewModel.updateWorkout(
//                workoutState.workout
//                    .copy(choice = it)
//            )}
//    )
//},
//onBackClick = onBackClick
//) { onNextCLick() }