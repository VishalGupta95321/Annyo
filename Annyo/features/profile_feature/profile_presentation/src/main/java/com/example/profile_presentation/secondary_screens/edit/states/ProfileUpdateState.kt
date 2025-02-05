package com.example.profile_presentation.secondary_screens.edit.states

sealed interface ProfileUpdateState{
    object NotUpdating : ProfileUpdateState
    object Updating : ProfileUpdateState
    object Updated : ProfileUpdateState
    data class UpdateFailed(
        val message : String? = null
    ): ProfileUpdateState
}