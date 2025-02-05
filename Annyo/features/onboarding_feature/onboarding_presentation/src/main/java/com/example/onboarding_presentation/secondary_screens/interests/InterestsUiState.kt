package com.example.onboarding_presentation.secondary_screens.interests

import com.example.profile.model.Interests

data class InterestsUiState(
    val interests: Interests = Interests(listOf()),
    val isLoading : Boolean = false,
)