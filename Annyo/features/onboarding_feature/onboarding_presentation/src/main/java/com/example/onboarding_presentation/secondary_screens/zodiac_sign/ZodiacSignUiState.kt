package com.example.onboarding_presentation.secondary_screens.zodiac_sign

import com.example.profile.model.ZodiacSign

data class ZodiacSignUiState(
    val zodiacSign: ZodiacSign = ZodiacSign(""),
    val isLoading: Boolean = false,
)
