package com.example.profile.remote.request

import com.example.profile.model.Gender
import kotlinx.serialization.Serializable

@Serializable
data class UpdateGenderRequest(
    val choice: String,
    val isVisibleInProfile: Boolean

){
    companion object {
        fun fromGender(
            gender: Gender
        ) =  UpdateGenderRequest(
            choice = gender.choice,
            isVisibleInProfile = gender.isVisibleInProfile
        )
    }
}