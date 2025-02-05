package com.example.onboarding_presentation


sealed interface ProfileUpdateState{
    object NotUpdating : ProfileUpdateState
    object Updating : ProfileUpdateState
    object Updated : ProfileUpdateState
    data class UpdateFailed(
        val message : String? = null
    ): ProfileUpdateState
}