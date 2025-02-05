package com.example.models.entity

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

data class User(
    val emailId: String,
    val name: Name,
    val drinking: String,
    val smoking: String,
    val recommendationPreferences: RecommendationPreferences,
    val collegeName: String?,
    val gender: String,
    val sexuality: String,
    val religion: String,
    val height: Double,
    val dob: Dob,
    val aboutChildren: String,
    val jobTitle: String?,
    val workPlace: String?,
    val location: Location,
    val writtenPrompts: List<WrittenPrompt>?,
    val photos: List<Photo>,
    val age: Int,
    @BsonId
    val uid: String,
){
}
