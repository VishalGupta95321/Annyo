package com.example.profile.remote.request

import com.example.profile.model.Ethnicity
import com.example.profile.model.Religion
import kotlinx.serialization.Serializable

@Serializable
data class UpdateReligionRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromReligion(
            religion : Religion
        ) = UpdateReligionRequest(
            choice = religion.choice,
            isVisibleInProfile = religion.isVisibleInProfile
        )
    }
}