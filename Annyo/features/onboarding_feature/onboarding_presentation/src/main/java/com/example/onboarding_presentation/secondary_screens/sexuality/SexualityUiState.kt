package com.example.onboarding_presentation.secondary_screens.sexuality

import com.example.profile.model.Sexuality

data class SexualityUiState(
    val sexuality: Sexuality = Sexuality(""),
    val isLoading: Boolean = false,
)
