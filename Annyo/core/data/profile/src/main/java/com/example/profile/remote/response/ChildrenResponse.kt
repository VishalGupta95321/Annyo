package com.example.profile.remote.response

import com.example.profile.model.Children

data class ChildrenResponse(
    val choice: String,
    val isVisibleInProfile: Boolean
){
    fun toChildren() = Children(
        choice, isVisibleInProfile
    )
}