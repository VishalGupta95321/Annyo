package com.example.onboarding_presentation.secondary_screens.children

import com.example.profile.model.Children

data class ChildrenUiState(
    val children: Children = Children(""),
    val isLoading : Boolean = false,
)