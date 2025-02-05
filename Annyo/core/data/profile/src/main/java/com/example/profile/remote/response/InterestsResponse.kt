package com.example.profile.remote.response

import com.example.profile.model.Interests

data class InterestsResponse(
    val interests: List<String>,
){
    fun toHobbies() = Interests(
        interests
    )
}