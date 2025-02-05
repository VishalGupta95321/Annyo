package com.example.profile.use_cases

import android.net.Uri
import com.example.profile.model.Photo
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import javax.inject.Inject


class UploadPhotoToCloudUseCase  @Inject constructor(
    private val repository: ProfileRepository
){
    suspend operator fun invoke(
        photo : Photo
    ):GetBack<Uri>{
        return repository.uploadPhotoToCloud(photo)
    }
}