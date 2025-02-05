package com.example.onboarding_presentation.secondary_screens.languages

import com.example.profile.model.Languages

//data class LanguagesUiState(
//    val languages: Languages = Languages(listOf()),
//    val isLoading : Boolean = false,
//)

sealed interface LanguagesUiState{

    object Loading : LanguagesUiState

    data class Success(
        val languages:Languages
        ) : LanguagesUiState

    object Error : LanguagesUiState
}