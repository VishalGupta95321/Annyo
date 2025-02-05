package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Languages(
    val languages: List<String>,
    val isVisibleInProfile: Boolean = true
):ProfileModel()