package com.example.profile.remote.request

import com.example.profile.model.College
import kotlinx.serialization.Serializable

@Serializable
data class UpdateCollegeRequest(
        val collegeName : String,
        val isVisibleInProfile: Boolean
){
        companion object {
                fun fromCollege(
                        college: College
                ) = UpdateCollegeRequest(
                        collegeName = college.collegeName,
                        isVisibleInProfile = college.isVisibleInProfile
                )
        }
}