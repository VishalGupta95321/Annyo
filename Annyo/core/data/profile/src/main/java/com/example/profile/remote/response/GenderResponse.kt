package com.example.profile.remote.response

import com.example.profile.model.Gender

data class GenderResponse(
    val choice: String,
    val isVisibleInProfile: Boolean

){
    fun toGender() = Gender(
        choice,isVisibleInProfile
    )
}