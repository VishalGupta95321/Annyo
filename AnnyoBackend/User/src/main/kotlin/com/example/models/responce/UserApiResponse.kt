package com.example.models.responce

import kotlinx.serialization.Serializable

@Serializable
data class UserApiResponse<T>(
    val data : T? = null,
)