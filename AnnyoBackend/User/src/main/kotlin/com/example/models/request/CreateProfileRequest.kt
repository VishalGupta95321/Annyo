package com.example.models.request

import com.example.models.entity.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(
    val user : User
)
