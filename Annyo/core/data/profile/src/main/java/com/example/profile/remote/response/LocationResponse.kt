package com.example.profile.remote.response

import com.example.profile.model.Location


data class LocationResponse(
    val latitude : Double,
    val longitude : Double,
    val address : String
){
    fun toLocation() = Location(
        latitude, longitude, address
    )
}