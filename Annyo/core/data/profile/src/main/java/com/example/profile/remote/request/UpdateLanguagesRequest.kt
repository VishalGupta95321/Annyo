package com.example.profile.remote.request

import com.example.profile.model.Languages
import kotlinx.serialization.Serializable


@Serializable
data class UpdateLanguagesRequest(
    val languages: List<String>,
    val isVisibleInProfile: Boolean
){
    companion object {
        fun fromLanguages(
           languages: Languages
        ) =  UpdateLanguagesRequest(
            languages = languages.languages,
            isVisibleInProfile = languages.isVisibleInProfile
        )
    }
}