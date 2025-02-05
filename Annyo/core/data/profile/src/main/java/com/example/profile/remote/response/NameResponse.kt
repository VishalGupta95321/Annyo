package com.example.profile.remote.response

import com.example.profile.model.Name

data class NameResponse(
    val name : String
){
    fun toName() = Name(
        name
    )
}