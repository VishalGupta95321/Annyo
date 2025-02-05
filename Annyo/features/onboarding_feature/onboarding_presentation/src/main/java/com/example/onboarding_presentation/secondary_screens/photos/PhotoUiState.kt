package com.example.onboarding_presentation.secondary_screens.photos

import com.example.profile.model.Photo

data class PhotoUiState(
    val photo1: Photo? = null,
    val photo2: Photo? = null,
    val photo3: Photo? = null,
    val photo4: Photo? = null,
    val photo5: Photo? = null,
    val photo6: Photo? = null,
    val isLoading: Boolean = false,
)

