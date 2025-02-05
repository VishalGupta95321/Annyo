package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Children(
    val choice: String,
    val isVisibleInProfile: Boolean = false
):ProfileModel()