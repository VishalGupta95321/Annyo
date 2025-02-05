package com.example.profile_presentation.secondary_screens.edit.states

import com.example.profile.model.Profile

sealed interface ProfileState{

    object Loading : ProfileState

    data class Success(
        val profile : Profile
    ) : ProfileState

    data class Error(
        val message : String? = null
    ) : ProfileState

}