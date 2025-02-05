package com.example.profile.remote.response

import com.example.profile.model.Languages

data class LanguagesResponse(
    val languages: List<String>,
    val isVisibleInProfile: Boolean
){
    fun toLanguages() = Languages(
        languages, isVisibleInProfile
    )
}