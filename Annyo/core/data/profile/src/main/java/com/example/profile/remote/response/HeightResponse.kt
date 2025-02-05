package com.example.profile.remote.response

import com.example.profile.model.Height

data class HeightResponse(
    val heightInFeet : String,
    val isVisibleInProfile: Boolean
){
    fun toHeight() = Height(
        heightInFeet, isVisibleInProfile
    )
}