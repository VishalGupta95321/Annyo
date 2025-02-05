package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Height(
    val heightInFeet : String = "",
    val isVisibleInProfile: Boolean = true
):ProfileModel()