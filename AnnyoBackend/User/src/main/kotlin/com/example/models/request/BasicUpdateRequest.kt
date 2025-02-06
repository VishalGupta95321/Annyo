package com.example.models.request

import com.example.util.BasicUpdateRequestType
import kotlinx.serialization.Serializable

@Serializable
data class BasicUpdateRequest(
    val data: String,
    val type: BasicUpdateRequestType
)