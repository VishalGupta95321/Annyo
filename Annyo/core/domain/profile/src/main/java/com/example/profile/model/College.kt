package com.example.profile.model

import com.example.profile.util.ProfileModel

data class College(
    val collegeName : String ,
    val isVisibleInProfile: Boolean = true
):ProfileModel()