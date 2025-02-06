package com.example.util

import kotlinx.serialization.Serializable

@Serializable
sealed interface BasicUpdateRequestType{
    object EmailId: BasicUpdateRequestType
    object Drinking: BasicUpdateRequestType
    object Smoking: BasicUpdateRequestType
    object CollegeName: BasicUpdateRequestType
    object Gender: BasicUpdateRequestType
    object Sexuality: BasicUpdateRequestType
    object Religion: BasicUpdateRequestType
    object HeightInFeet: BasicUpdateRequestType
    object Age: BasicUpdateRequestType
    object AboutChildren: BasicUpdateRequestType
    object JobTitle: BasicUpdateRequestType
    object WorkPlace: BasicUpdateRequestType
}