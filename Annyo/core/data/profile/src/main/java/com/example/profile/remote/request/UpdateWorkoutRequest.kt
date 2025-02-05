package com.example.profile.remote.request

import com.example.profile.model.Workout
import kotlinx.serialization.Serializable


@Serializable
data class UpdateWorkoutRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromWorkout(
            workout: Workout
        ) =  UpdateWorkoutRequest(
            choice = workout.choice,
            isVisibleInProfile = workout.isVisibleInProfile
        )
    }
}