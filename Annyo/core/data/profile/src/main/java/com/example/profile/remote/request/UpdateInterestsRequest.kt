package com.example.profile.remote.request

import com.example.profile.model.Interests
import kotlinx.serialization.Serializable


@Serializable
data class UpdateInterestsRequest(
    val hobbies: List<String>,
){
    companion object {
        fun fromHobbies(
            interests: Interests
        ) = UpdateInterestsRequest(
            hobbies = interests.interests
        )
    }
}