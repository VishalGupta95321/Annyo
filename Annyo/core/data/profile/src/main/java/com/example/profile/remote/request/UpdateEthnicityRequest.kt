package com.example.profile.remote.request

import com.example.profile.model.Ethnicity
import kotlinx.serialization.Serializable

@Serializable
data class UpdateEthnicityRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromEthnicity(
            ethnicity: Ethnicity
        ) = UpdateEthnicityRequest(
            choice = ethnicity.choice,
            isVisibleInProfile = ethnicity.isVisibleInProfile
        )
    }
}