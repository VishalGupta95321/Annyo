package com.example.profile.remote.response

import com.example.profile.model.Workout

data class WorkoutResponse(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    fun toWorkOut() = Workout(
        choice, isVisibleInProfile
    )
}