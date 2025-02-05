package com.example.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class WrittenPrompt(
    val prompt: String,
    val answer: String
)