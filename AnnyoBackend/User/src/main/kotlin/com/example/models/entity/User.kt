package com.example.models.entity

import com.example.models.responce.ProfileResponse
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
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
    fun toProfileResponse() = ProfileResponse(
        emailId,name,drinking,smoking,
        recommendationPreferences,collegeName,gender,
        sexuality,religion, height,dob,aboutChildren,
        jobTitle, workPlace, location, writtenPrompts,
        photos,age
    )
}
