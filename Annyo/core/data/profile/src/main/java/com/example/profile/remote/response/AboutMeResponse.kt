package com.example.profile.remote.response

import com.example.profile.model.AboutMe

data class AboutMeResponse(
    val aboutYou : String
){
    fun toAboutMe() = AboutMe(
        aboutYou = aboutYou
    )
}