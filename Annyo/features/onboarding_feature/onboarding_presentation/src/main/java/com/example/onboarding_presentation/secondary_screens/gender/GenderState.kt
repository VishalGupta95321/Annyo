package com.example.onboarding_presentation.secondary_screens.gender

import com.example.profile.model.Gender

data class GenderUiState(
    val gender: Gender = Gender(""),
    val isLoading: Boolean = false,
)
