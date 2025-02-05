package com.example.profile.remote.response

import com.example.profile.model.College

data class CollegeResponse(
    val collegeName : String,
    val isVisibleInProfile: Boolean
){
    fun toCollege() = College(
        collegeName, isVisibleInProfile
    )
}