package com.example.onboarding_presentation.secondary_screens.smoking

import com.example.profile.model.Smoking


data class SmokingUiState(
    val smoking: Smoking = Smoking(""),
    val isLoading: Boolean = false,
)
