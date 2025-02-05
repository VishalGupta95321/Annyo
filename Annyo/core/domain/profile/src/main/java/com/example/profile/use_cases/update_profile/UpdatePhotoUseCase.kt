package com.example.profile.use_cases.update_profile

import com.example.profile.model.Photo
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic
import javax.inject.Inject

class UpdatePhotoUseCase @Inject constructor(
    private val repository: ProfileRepository

) {
    suspend operator fun invoke(
        photo: Photo,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(photo)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(photo)
            }
        else
            repository.updateProfileToDataStore(photo)
    }
}