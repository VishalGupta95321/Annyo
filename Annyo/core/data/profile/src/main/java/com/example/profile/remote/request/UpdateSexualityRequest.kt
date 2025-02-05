package com.example.profile.remote.request

import com.example.profile.model.Gender
import com.example.profile.model.Sexuality
import kotlinx.serialization.Serializable


@Serializable
data class UpdateSexualityRequest(
    val choice: String,
    val isVisibleInProfile: Boolean

){
    companion object {
        fun fromSexuality(
            sexuality : Sexuality
        ) =  UpdateSexualityRequest(
            choice = sexuality.choice,
            isVisibleInProfile = sexuality.isVisibleInProfile
        )
    }
}