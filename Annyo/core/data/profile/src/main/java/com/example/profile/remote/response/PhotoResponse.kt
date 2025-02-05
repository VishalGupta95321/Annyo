package com.example.profile.remote.response

import com.example.profile.model.Photo

data class PhotoResponse(
    val photoUrl : String,
    val caption : String? = null,
    val photoCount : Int
){
    fun toPhoto() = Photo(
        photoUrl,photoCount,caption
    )
}
