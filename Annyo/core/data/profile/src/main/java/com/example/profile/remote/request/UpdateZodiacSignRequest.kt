package com.example.profile.remote.request

import com.example.profile.model.ZodiacSign
import kotlinx.serialization.Serializable


@Serializable
data class UpdateZodiacSignRequest(
    val sign : String,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromZodiacSign(
            zodiacSign: ZodiacSign
        ) = UpdateZodiacSignRequest(
            sign = zodiacSign.sign,
            isVisibleInProfile  = zodiacSign.isVisibleInProfile
        )
    }
}