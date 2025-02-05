package com.example.profile.remote.request

import com.example.profile.model.Height
import kotlinx.serialization.Serializable

@Serializable
data class UpdateHeightRequest(
    val heightInFeet : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromHeight(
            height: Height
        ) =  UpdateHeightRequest(
            heightInFeet = height.heightInFeet,
            isVisibleInProfile = height.isVisibleInProfile
        )
    }
}