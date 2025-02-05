package com.example.profile.remote.request

import com.example.profile.model.Location
import kotlinx.serialization.Serializable


@Serializable
data class UpdateLocationRequest(
    val latitude : Double,
    val longitude : Double,
    val address : String
){
    companion object {
        fun fromLocation(
            location: Location
        ) =  UpdateLocationRequest(
            latitude = location.latitude,
            longitude = location.longitude,
            address = location.address
        )
    }
}