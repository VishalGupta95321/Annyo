package com.example.profile.remote.request

import com.example.profile.model.AboutMe
import kotlinx.serialization.Serializable

@Serializable
data class UpdateAboutMeRequest(
    val aboutYou : String
){
    companion object{
        fun fromAboutMe(
            aboutMe: AboutMe
        ) = UpdateAboutMeRequest(
            aboutYou = aboutMe.aboutYou
        )
    }
}