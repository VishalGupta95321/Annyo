package com.example.models.request

import com.example.models.entity.Name
import kotlinx.serialization.Serializable

@Serializable
data class UpdateNameRequest(
    val  name: Name
)