package com.example.models.request

import com.example.models.entity.Location
import kotlinx.serialization.Serializable

@Serializable
data class UpdateLocationRequest(
    val location: Location
)