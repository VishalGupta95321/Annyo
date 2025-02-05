package com.example.profile.remote.request

import com.example.profile.model.Photo
import kotlinx.serialization.Serializable


@Serializable
data class UpdatePhotoRequest(       /// todo add set as profile photo variable
    val photoUrl : String,
    val caption : String? = null,
    val photoCount : Int
){
    companion object {
        fun fromPhoto(
            photo: Photo
        ) =   UpdatePhotoRequest(
            photoUrl = photo.photoUrl,
            photoCount = photo.photoCount,
            caption = photo.caption
        )
    }
}