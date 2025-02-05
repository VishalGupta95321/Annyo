package com.example.onboarding_presentation.secondary_screens.workout

import com.example.profile.model.Workout

data class WorkoutUiState(
    val workout: Workout = Workout(""),
    val isLoading: Boolean = false,
)
