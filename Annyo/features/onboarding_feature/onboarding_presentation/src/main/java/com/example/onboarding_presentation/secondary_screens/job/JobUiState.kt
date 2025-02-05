package com.example.onboarding_presentation.secondary_screens.job

import com.example.profile.model.Job

data class JobUiState(
    val job: Job = Job(),
    val isLoading : Boolean = false,
)