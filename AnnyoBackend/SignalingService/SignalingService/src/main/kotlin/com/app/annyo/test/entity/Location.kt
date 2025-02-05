package com.example.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class Location(
    val latitude: String,
    val longitude: String,
)