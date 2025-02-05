package com.example.profile.model

import com.example.profile.util.ProfileModel

data class ZodiacSign(
    val sign : String,
    val isVisibleInProfile: Boolean = true
):ProfileModel()