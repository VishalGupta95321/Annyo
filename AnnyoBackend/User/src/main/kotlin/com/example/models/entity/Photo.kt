package com.example.models.entity

import kotlinx.serialization.Serializable
@Serializable
data class Photo (
    val id: String,
    val uri: String,
)