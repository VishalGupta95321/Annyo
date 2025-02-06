package com.example.models.request

import com.example.models.entity.Photo
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePhotoRequest(
    val photoCount: Int,
    val id: String,
    val uri: String,
){
    fun toPhoto() = Photo(
        id = id,
        uri = uri
    )
}
