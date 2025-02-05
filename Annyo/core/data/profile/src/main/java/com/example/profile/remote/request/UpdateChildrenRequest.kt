package com.example.profile.remote.request

import com.example.profile.model.Children
import kotlinx.serialization.Serializable

@Serializable
data class UpdateChildrenRequest(
    val choice : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromChildren(
            children: Children
        ) = UpdateChildrenRequest(
            choice = children.choice,
            isVisibleInProfile= children.isVisibleInProfile
        )
    }
}