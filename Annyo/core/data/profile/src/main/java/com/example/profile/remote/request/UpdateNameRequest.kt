package com.example.profile.remote.request

import com.example.profile.model.Name
import kotlinx.serialization.Serializable


@Serializable
data class UpdateNameRequest(
    val name : String
){
    companion object {
        fun fromName(
           name: Name
        ) =  UpdateNameRequest(
            name = name.name
        )
    }
}