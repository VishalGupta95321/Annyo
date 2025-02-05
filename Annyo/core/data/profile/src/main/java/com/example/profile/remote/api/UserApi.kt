package com.example.profile.remote.api

import com.example.profile.remote.request.*
import com.example.profile.remote.response.ProfileResponse
import com.example.profile.remote.response.SimpleUserApiResponse
import com.example.profile.remote.response.UserApiResponse
import retrofit2.http.*

interface UserApi {

    @GET("/profile")
    suspend fun getProfile(): UserApiResponse<ProfileResponse>

    @POST("/create/newsUser")
    suspend fun createProfile(
        @Body request: CreateProfileRequest
    ): SimpleUserApiResponse

    @PATCH("/update/name")
    suspend fun updateName(
        @Body request : UpdateNameRequest
    ): SimpleUserApiResponse

    @PATCH("/update/dob")
    suspend fun updateDateOfBirth(
        @Body request : UpdateDobRequest
    ): SimpleUserApiResponse

    @PATCH("/update/location")
    suspend fun updateLocation(
        @Body request : UpdateLocationRequest
    ): SimpleUserApiResponse

    @PATCH("/update/workout")
    suspend fun updateWorkout(
        @Body request : UpdateWorkoutRequest
    ): SimpleUserApiResponse

    @PATCH("/update/photo")
    suspend fun updatePhoto(
        @Body request : UpdatePhotoRequest
    ): SimpleUserApiResponse

    @PATCH("/update/prompt/gender")
    suspend fun updateGender(
        @Body request : UpdateGenderRequest
    ): SimpleUserApiResponse


    @PATCH("/update/prompt/sexuality")
    suspend fun updateSexuality(
        @Body request : UpdateSexualityRequest
    ): SimpleUserApiResponse

    @PATCH("/update/prompt/pets")
    suspend fun updatePets(
        @Body request : UpdatePetsRequest
    ): SimpleUserApiResponse

    @PATCH("/update/children")
    suspend fun updateChildren(
        @Body request : UpdateChildrenRequest
    ): SimpleUserApiResponse

    @PATCH("/update/ethnicity")
    suspend fun updateEthnicity(
        @Body request : UpdateEthnicityRequest
    ): SimpleUserApiResponse

    @PATCH("/update/religion")
    suspend fun updateReligion(
        @Body request : UpdateReligionRequest
    ): SimpleUserApiResponse

    @PATCH("/update/smoking")
    suspend fun updateSmoking(
        @Body request : UpdateSmokingRequest
    ): SimpleUserApiResponse

    @PATCH("/update/drinking")
    suspend fun updateDrinking(
        @Body request : UpdateDrinkingRequest
    ): SimpleUserApiResponse

    @PATCH("/update/languages")
    suspend fun updateLanguages(
        @Body request : UpdateLanguagesRequest
    ): SimpleUserApiResponse

    @PATCH("/update/height")
    suspend fun updateHeight(
           @Body request : UpdateHeightRequest
    ): SimpleUserApiResponse

    @PATCH("/update/college")
    suspend fun updateCollege(
        @Body request : UpdateCollegeRequest
    ): SimpleUserApiResponse

    @PATCH("/update/job")
    suspend fun updateJob(
        @Body request : UpdateJobRequest
    ): SimpleUserApiResponse

    @PATCH("/update/zodiacSign")
    suspend fun updateZodiacSign(
        @Body request : UpdateZodiacSignRequest
    ): SimpleUserApiResponse

    @PATCH("/update/aboutMe")
    suspend fun updateAboutMe(
        @Body request : UpdateAboutMeRequest
    ): SimpleUserApiResponse

    @PATCH("/update/hobbies")
    suspend fun updateInterests(
        @Body request : UpdateInterestsRequest
    ): SimpleUserApiResponse

    companion object {
        const val BASE_URL = "https://www.google.com"
    }
}


//    @DELETE("/delete")
//    suspend fun deleteAccount(
//        //  @Body createProfileRequest: CreateProfileRequest
//    ):SimpleUserApiResponse
