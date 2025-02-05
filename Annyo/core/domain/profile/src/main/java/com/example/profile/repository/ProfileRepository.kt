package com.example.profile.repository


import android.net.Uri
import com.example.profile.model.Photo
import com.example.profile.model.Profile
import com.example.profile.util.ProfileModel
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun createProfile(
        profile: Profile
    ): GetBackBasic

    fun getProfile(): Flow<Profile>

    fun testProfile(): Flow<GetBack<Flow<Profile>>>

    suspend fun syncProfileWithServer(): GetBackBasic

    suspend fun updateProfileToDataStore(
        requestType: ProfileModel,
    ): GetBackBasic

    suspend fun updateProfileToServer(
        requestType: ProfileModel,
    ): GetBackBasic

    suspend fun uploadPhotoToCloud(
        photo: Photo
    ): GetBack<Uri>   /// + caption

}
