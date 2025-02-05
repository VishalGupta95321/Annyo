package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Job(
    val jobTitle : String = "",
    val isVisibleInProfile: Boolean = true
):ProfileModel()