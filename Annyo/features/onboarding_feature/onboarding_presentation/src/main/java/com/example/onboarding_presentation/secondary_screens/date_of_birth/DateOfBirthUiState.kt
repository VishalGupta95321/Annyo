package com.example.onboarding_presentation.secondary_screens.date_of_birth

import com.example.profile.model.DateOfBirth

data class DateOfBirthUiState(
    val dateOfBirth : DateOfBirth = DateOfBirth(0,0,0),
    val isLoading : Boolean = false,
)