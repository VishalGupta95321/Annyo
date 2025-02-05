package com.example.notification.call.model

import androidx.core.graphics.drawable.IconCompat

data class Individual(
    val name: String,
    val pic: IconCompat,    //// todo maybe we can take drawable or bitmap etc
    val isImportant: Boolean = true
)
