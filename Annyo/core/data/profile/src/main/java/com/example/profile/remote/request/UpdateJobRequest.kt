package com.example.profile.remote.request

import com.example.profile.model.Job
import kotlinx.serialization.Serializable


@Serializable
data class UpdateJobRequest(
    val jobTitle : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromJob(
            job: Job
        ) = UpdateJobRequest(
            jobTitle = job.jobTitle,
            isVisibleInProfile = job.isVisibleInProfile
        )
    }
}