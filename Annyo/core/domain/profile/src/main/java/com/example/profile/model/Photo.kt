package com.example.profile.model

import com.example.profile.util.ProfileModel

data class Photo(
    val photoUrl : String,
    val photoCount : Int,
    val caption : String? = null,
):ProfileModel()     /// todo implement Uri encoder / decoder
