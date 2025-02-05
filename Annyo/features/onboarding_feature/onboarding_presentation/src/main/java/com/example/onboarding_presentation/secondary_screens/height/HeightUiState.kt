package com.example.onboarding_presentation.secondary_screens.height

import com.example.profile.model.Height

data class HeightUiState(
    val height : Height = Height(""),
    val isLoading : Boolean = false,
)