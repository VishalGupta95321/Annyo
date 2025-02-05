package com.example.onboarding_presentation.secondary_screens.location

import com.example.profile.model.Location

data class LocationUiState(
    val location: Location = Location(0.0,0.0,""),
    val isLoading : Boolean = false,
)