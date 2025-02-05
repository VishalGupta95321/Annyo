package com.example.onboarding_presentation.secondary_screens.ethnicity

import com.example.profile.model.Ethnicity

data class EthnicityUiState(
    val ethnicity: Ethnicity = Ethnicity(""),
    val isLoading: Boolean = false,
)
