package com.example.repository

import com.example.models.request.*
import com.example.models.responce.BasicResponse
import com.example.models.responce.ProfileResponse
import com.example.util.Response

interface UserRepository {
    suspend fun createProfile(request: CreateProfileRequest):Response<BasicResponse>
    suspend fun updateBasicProfile(uid:String,request: BasicUpdateRequest):Response<BasicResponse>
    suspend fun updateLocation(uid:String,request: UpdateLocationRequest):Response<BasicResponse>
    suspend fun updateRecommendationPreferences(
        uid:String,request: UpdateRecommendationPreferencesRequest
    ):Response<BasicResponse>
    suspend fun updatePhoto(uid:String,request: UpdatePhotoRequest):Response<BasicResponse>
    suspend fun updateWrittenPrompt(uid:String,request: UpdateWrittenPromptRequest):Response<BasicResponse>
    suspend fun updateDob(uid:String,request: UpdateDobRequest):Response<BasicResponse>
    suspend fun updateName(uid:String,request: UpdateNameRequest):Response<BasicResponse>
    suspend fun getUserProfile(uid:String):Response<ProfileResponse>
    suspend fun getUserProfiles(cellId:String):Response<List<ProfileResponse>>
    suspend fun deleteUserProfile(uid:String):Response<BasicResponse>
}