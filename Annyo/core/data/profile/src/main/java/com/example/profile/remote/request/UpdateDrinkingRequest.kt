package com.example.profile.remote.request

import com.example.profile.model.Drinking
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDrinkingRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromDrinking(
            drinking: Drinking
        ) = UpdateDrinkingRequest(
            choice = drinking.choice,
            isVisibleInProfile = drinking.isVisibleInProfile
        )
    }
}