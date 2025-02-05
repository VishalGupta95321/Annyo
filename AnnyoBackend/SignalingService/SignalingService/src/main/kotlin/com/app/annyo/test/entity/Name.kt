package com.example.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class Name(
    val firstName: String,
    val lastName: String?
)