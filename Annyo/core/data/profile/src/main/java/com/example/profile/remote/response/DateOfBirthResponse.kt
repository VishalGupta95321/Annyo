package com.example.profile.remote.response

import com.example.profile.model.DateOfBirth

data class DateOfBirthResponse(
    val day : Int,
    val month : Int,
    val year : Int,
    val isVisibleInProfile: Boolean
){
    fun toDateOfBirth() = DateOfBirth(
        day, month, year, isVisibleInProfile
    )
}