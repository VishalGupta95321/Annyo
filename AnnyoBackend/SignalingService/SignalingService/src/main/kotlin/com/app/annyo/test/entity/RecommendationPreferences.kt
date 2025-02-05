package com.example.models.entity

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationPreferences(
    val gender : String,
    val sexuality: String,
    val maxAgeLimit: Int,
    val minAgeLimit: Int,
    val maxDistanceInMiles: Double,
    val religion: String,
    val smoking: String?,
    val drinking: String?,
    val aboutChildren: String?,
    val height: Double?
)
