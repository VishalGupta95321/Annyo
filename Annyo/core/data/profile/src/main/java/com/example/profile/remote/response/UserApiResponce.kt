package com.example.profile.remote.response

typealias SimpleUserApiResponse = UserApiResponse<Nothing>

data class UserApiResponse<T>(
    val isSuccessful : Boolean,
    val message : String? = null,
    val data : T?  = null
)