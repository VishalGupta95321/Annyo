package com.example.onboarding_presentation.secondary_screens.college

import com.example.profile.model.College

data class CollegeUiState(
    val college: College = College(""),
    val isLoading : Boolean = false,
)