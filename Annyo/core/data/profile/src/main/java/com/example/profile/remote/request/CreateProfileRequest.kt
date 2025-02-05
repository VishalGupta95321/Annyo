package com.example.profile.remote.request

import com.example.profile.model.Profile
import com.example.profile.remote.request.UpdateAboutMeRequest.Companion.fromAboutMe
import com.example.profile.remote.request.UpdateChildrenRequest.Companion.fromChildren
import com.example.profile.remote.request.UpdateCollegeRequest.Companion.fromCollege
import com.example.profile.remote.request.UpdateDobRequest.Companion.fromDob
import com.example.profile.remote.request.UpdateEthnicityRequest.Companion.fromEthnicity
import com.example.profile.remote.request.UpdateGenderRequest.Companion.fromGender
import com.example.profile.remote.request.UpdateHeightRequest.Companion.fromHeight
import com.example.profile.remote.request.UpdateInterestsRequest.Companion.fromHobbies
import com.example.profile.remote.request.UpdateJobRequest.Companion.fromJob
import com.example.profile.remote.request.UpdateLanguagesRequest.Companion.fromLanguages
import com.example.profile.remote.request.UpdateLocationRequest.Companion.fromLocation
import com.example.profile.remote.request.UpdateNameRequest.Companion.fromName
import com.example.profile.remote.request.UpdatePetsRequest.Companion.fromPets
import com.example.profile.remote.request.UpdatePhotoRequest.Companion.fromPhoto
import com.example.profile.remote.request.UpdateSexualityRequest.Companion.fromSexuality
import com.example.profile.remote.request.UpdateSmokingRequest.Companion.fromSmoking
import com.example.profile.remote.request.UpdateWorkoutRequest.Companion.fromWorkout
import com.example.profile.remote.request.UpdateZodiacSignRequest.Companion.fromZodiacSign


import kotlinx.serialization.Serializable

@Serializable
data class CreateProfileRequest(
    val uid : String,
    val aboutMe: UpdateAboutMeRequest,
    val name : UpdateNameRequest,
    val dateOfBirth: UpdateDobRequest,
    val workout: UpdateWorkoutRequest,
    val pets: UpdatePetsRequest,
    val children: UpdateChildrenRequest,
    val smoking: UpdateSmokingRequest,
    val ethnicity: UpdateEthnicityRequest,
    val gender: UpdateGenderRequest,
    val sexuality: UpdateSexualityRequest,
    val languages: UpdateLanguagesRequest,
    val interests: UpdateInterestsRequest,
    val college: UpdateCollegeRequest,
    val job: UpdateJobRequest,
    val height: UpdateHeightRequest,
    val location: UpdateLocationRequest,
    val zodiacSign: UpdateZodiacSignRequest,
    val photo1 : UpdatePhotoRequest?,
    val photo2 : UpdatePhotoRequest?,
    val photo3 : UpdatePhotoRequest?,
    val photo4 : UpdatePhotoRequest?,
    val photo5 : UpdatePhotoRequest?,
    val photo6 : UpdatePhotoRequest?,
){
    companion object {
        fun fromProfile(
            profile: Profile
        ):CreateProfileRequest = CreateProfileRequest(
            uid = "",
            aboutMe = fromAboutMe(profile.aboutMe),
            name = fromName(profile.name),
            dateOfBirth = fromDob(profile.dateOfBirth),
            workout = fromWorkout(profile.workout),
            pets = fromPets(profile.pets),
            children = fromChildren(profile.children),
            smoking = fromSmoking(profile.smoking),
            ethnicity = fromEthnicity(profile.ethnicity),
            gender = fromGender(profile.gender),
            sexuality = fromSexuality(profile.sexuality),
            languages = fromLanguages(profile.languages),
            interests = fromHobbies(profile.interests),
            college = fromCollege(profile.college),
            job = fromJob(profile.job),
            height = fromHeight(profile.height),
            location = fromLocation(profile.location),
            zodiacSign = fromZodiacSign(profile.zodiacSign),
            photo1 = profile.photo1?.let { fromPhoto(it) },
            photo2 = profile.photo2?.let { fromPhoto(it) },
            photo3 = profile.photo3?.let { fromPhoto(it) },
            photo4 = profile.photo4?.let { fromPhoto(it) },
            photo5 = profile.photo5?.let { fromPhoto(it) },
            photo6 = profile.photo6?.let { fromPhoto(it) },
        )
    }
}