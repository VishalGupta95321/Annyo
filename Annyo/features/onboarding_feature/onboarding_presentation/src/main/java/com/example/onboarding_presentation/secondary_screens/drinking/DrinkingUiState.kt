package com.example.onboarding_presentation.secondary_screens.drinking

import com.example.profile.model.Drinking

data class DrinkingUiState(
    val drinking : Drinking = Drinking(""),
    val isLoading : Boolean = false,
)