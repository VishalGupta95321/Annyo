package com.example.profile.remote.request

import com.example.profile.model.Pets
import kotlinx.serialization.Serializable


@Serializable
data class UpdatePetsRequest(
    val choice: String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromPets(
            pets: Pets
        ) =  UpdatePetsRequest(
            choice = pets.choice,
            isVisibleInProfile = pets.isVisibleInProfile
        )
    }
}