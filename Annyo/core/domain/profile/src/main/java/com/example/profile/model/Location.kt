package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Location(
    val latitude : Double,
    val longitude : Double,
    val address : String
):ProfileModel()