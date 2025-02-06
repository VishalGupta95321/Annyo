package com.example.models.responce

import kotlinx.serialization.Serializable

@Serializable
data class BasicResponse(
    val status : Boolean = true,
)