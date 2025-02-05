package com.example.onboarding_presentation.secondary_screens.about_me

import com.example.profile.model.AboutMe

data class AboutMeUiState(
    val aboutMe: AboutMe = AboutMe(""),
    val isLoading : Boolean = false,
)