package com.example.profile.remote.response

import com.example.profile.model.Gender
import com.example.profile.model.Sexuality

data class SexualityResponse(
    val choice: String,
    val isVisibleInProfile: Boolean

){
    fun toSexuality() = Sexuality(
        choice,isVisibleInProfile
    )
}