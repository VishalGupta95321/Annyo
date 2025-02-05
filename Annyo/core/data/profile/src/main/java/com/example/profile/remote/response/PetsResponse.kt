package com.example.profile.remote.response

import com.example.profile.model.Pets

data class PetsResponse(
    val choice: String,
    val isVisibleInProfile: Boolean
){
    fun toPets() = Pets(
        choice, isVisibleInProfile
    )
}
