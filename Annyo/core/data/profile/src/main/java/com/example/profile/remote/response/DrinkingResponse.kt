package com.example.profile.remote.response

import com.example.profile.model.Drinking

data class DrinkingResponse(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    fun toDrinking()  = Drinking(
        choice, isVisibleInProfile
    )
}