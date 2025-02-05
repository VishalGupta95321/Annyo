package com.example.profile.remote.response

import com.example.profile.model.Job

data class JobResponse(
    val jobTitle : String,
    val isVisibleInProfile: Boolean
){
    fun toJob()  = Job(
        jobTitle, isVisibleInProfile
    )
}