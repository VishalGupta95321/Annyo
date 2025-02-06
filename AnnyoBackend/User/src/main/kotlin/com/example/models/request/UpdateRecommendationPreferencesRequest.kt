package com.example.models.request

import com.example.models.entity.RecommendationPreferences
import kotlinx.serialization.Serializable

@Serializable
data class UpdateRecommendationPreferencesRequest(
    val gender : String,
    val sexuality: String,
    val maxAgeLimit: Int,
    val minAgeLimit: Int,
    val maxDistanceInMiles: Double,
    val religion: String,
    val smoking: String?,
    val drinking: String?,
    val aboutChildren: String?,
    val height: Double?,
){
    fun toRecommendationPreferences() = RecommendationPreferences(
        gender = gender,
        sexuality = sexuality,
        minAgeLimit = minAgeLimit,
        maxAgeLimit = maxAgeLimit,
        maxDistanceInMiles = maxDistanceInMiles,
        religion = religion,
        smoking = smoking,
        drinking = drinking,
        aboutChildren = aboutChildren,
        height = height
    )
}