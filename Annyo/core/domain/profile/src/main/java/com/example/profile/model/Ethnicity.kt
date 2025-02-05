package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Ethnicity(
    val choice : String,
    val isVisibleInProfile: Boolean = true
):ProfileModel()