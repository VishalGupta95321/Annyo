package com.example.profile.remote.response

import com.example.profile.model.Religion

data class ReligionResponse(
    val choice: String,
    val isVisibleInProfile: Boolean
){
    fun toReligion() = Religion(
        choice, isVisibleInProfile
    )
}