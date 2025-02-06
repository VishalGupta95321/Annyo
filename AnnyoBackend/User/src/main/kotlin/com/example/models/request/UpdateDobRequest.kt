package com.example.models.request

import com.example.models.entity.Dob
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDobRequest(
    val dob: Dob
)