package com.example.profile.remote.response

import com.example.profile.model.ZodiacSign

data class ZodiacSignResponse(
    val sign : String,
    val isVisibleInProfile: Boolean
){
    fun toZodiacSign() = ZodiacSign(
        sign, isVisibleInProfile
    )
}