package com.example.profile.remote.request

import com.example.profile.model.Smoking
import kotlinx.serialization.Serializable


@Serializable
data class UpdateSmokingRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromSmoking(
            smoking: Smoking
        ) =   UpdateSmokingRequest(
            choice = smoking.choice,
            isVisibleInProfile = smoking.isVisibleInProfile
        )
    }
}