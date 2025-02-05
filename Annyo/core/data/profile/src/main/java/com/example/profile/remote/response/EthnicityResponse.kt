package com.example.profile.remote.response

import com.example.profile.model.Ethnicity

data class EthnicityResponse(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    fun  toEthnicity() = Ethnicity(
        choice, isVisibleInProfile
    )
}