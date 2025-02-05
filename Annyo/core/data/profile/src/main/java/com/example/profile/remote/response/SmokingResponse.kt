package com.example.profile.remote.response

import com.example.profile.model.Smoking

data class SmokingResponse(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    fun toSmoking() = Smoking(
        choice, isVisibleInProfile
    )
}