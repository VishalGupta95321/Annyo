package com.example.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class Dob (
    val day : Int,
    val month : Int,
    val year : Int
)
