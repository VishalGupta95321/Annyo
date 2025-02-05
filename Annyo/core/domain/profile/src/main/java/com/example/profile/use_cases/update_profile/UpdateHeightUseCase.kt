package com.example.profile.use_cases.update_profile

import com.example.profile.model.Height
import com.example.profile.model.Photo
import com.example.profile.repository.ProfileRepository
import com.example.utils.GetBack
import com.example.utils.GetBackBasic

class UpdateHeightUseCase(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(
        height: Height,
        updateToServer : Boolean = true
    ):GetBackBasic{

        return if (updateToServer)
            return when(
                val serverRes = repository.updateProfileToServer(height)
            ){
                is GetBack.Error -> {
                    GetBack.Error(serverRes.message)
                }
                is GetBack.Success ->
                    repository.updateProfileToDataStore(height)
            }
        else
            repository.updateProfileToDataStore(height)
    }

}