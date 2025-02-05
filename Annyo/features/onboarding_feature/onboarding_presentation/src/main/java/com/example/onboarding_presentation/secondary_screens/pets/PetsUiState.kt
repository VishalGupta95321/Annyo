package com.example.onboarding_presentation.secondary_screens.pets

import com.example.profile.model.Pets

data class PetsUiState(
    val pets: Pets = Pets(""),
    val isLoading: Boolean = false,
)
