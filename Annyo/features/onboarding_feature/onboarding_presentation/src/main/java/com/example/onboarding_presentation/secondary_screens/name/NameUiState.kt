package com.example.onboarding_presentation.secondary_screens.name

import com.example.profile.model.Name

data class NameUiState(
    val name: Name = Name(""),
    val isLoading : Boolean = false,
)