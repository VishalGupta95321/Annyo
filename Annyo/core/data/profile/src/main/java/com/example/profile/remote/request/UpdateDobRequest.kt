package com.example.profile.remote.request

import com.example.profile.model.DateOfBirth
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDobRequest(
    val day : Int,
    val month : Int,
    val year : Int,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromDob(
            dateOfBirth: DateOfBirth
        ) = UpdateDobRequest(
            day = dateOfBirth.day,
            month = dateOfBirth.month,
            year = dateOfBirth.year,
            isVisibleInProfile = dateOfBirth.isVisibleInProfile
        )
    }
}