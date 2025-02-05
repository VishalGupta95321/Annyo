package com.example.onboarding_presentation.secondary_screens.religion

import com.example.profile.model.Religion

data class ReligionUiState(
    val religion: Religion = Religion(""),
    val isLoading: Boolean = false,
)
